#!/usr/bin/python3
#
# Author Jerry CHEN
# 以下接口基于上海发布提供的数据源，以获取实时公交信息
#
from flask import Blueprint, request
from collections import OrderedDict
from lxml import etree
import json
import requests

bus_api = Blueprint('bus_api', __name__)


"""  Initialization """
URL_SID = 'https://shanghaicity.openservice.kankanews.com/public/bus/get'
URL_STOPS = 'https://shanghaicity.openservice.kankanews.com/public/bus/mes/sid/'
URL_STATUS = 'https://shanghaicity.openservice.kankanews.com/public/bus/Getstop'

header_sid = {
    "accept": "*/*",
    "accept-Encoding": "gzip, deflate, br",
    "Accept-Language": "zh-CN,zh;q=0.9,en;q=0.8,en-GB;q=0.7,en-US;q=0.6",
    "Connection": "keep-alive",
    "Content-Type": "application/x-www-form-urlencoded",
    "content-length": "18",
    "cookie":"_ga=GA1.2.842443168.1616510534; souid=wKgBHGBgbWFt4k2mmpwoAg==; iwt_uuid=7f85f7f2-62b3-482c-b772-c05945b60977; last_search_records=eyJpdiI6IjVMUzJoN1dBTGZoY3p3akVFTXprVUE9PSIsInZhbHVlIjoiUEhwZEVEbkp1eFFLdW1oeU5uMHVBVjFhXC9kWjBFMEpkMDFOTTNBb0lnclk9IiwibWFjIjoiNjY3YjY5YmQ3YjYwZjcwYjMxMDdkNGVlYzZiN2RhMDUwODE5NzZiZmQyYjQ2MGFhYjEyOGJmOWMxMjM3NjBlMiJ9; acw_tc=76b20f6216169430160344673e1f658cd7d8bbc17fa30f8209cd13dc639a7e; XSRF-TOKEN=eyJpdiI6InpFWjNPbEpHbUJtdFlQN0xEc1Iza2c9PSIsInZhbHVlIjoiSnNudXdVSEJZNGM4Q01tRVd1R2xQZVBnZmE5bEx6T3puZXo1VWo3N2FiK0ZtdE1vcFFLNDdLS2xoK2dwRDg4OXhqYnUrdG9rNG9XN1AwaFBYam1ONFE9PSIsIm1hYyI6ImQyZDVhNTcwOWNiNzhmOTNhODNlN2YwMmY4ZGI1MjI1ZWEyMGVlNGUzNmZlNGMxNTYxODkxYWRhYTVkNzA1NWEifQ==; _session=eyJpdiI6IkJNMDk0SjZKaWl2ZXNKamx1NDZTcnc9PSIsInZhbHVlIjoiQVZWbVBZaitRR0dvTGJZSmsrXC9WR29TNzZ3K3l0SWVTZ2tCXC9ZY1EwXC9kNzVxMFhpY0ljeG9kZThSdjJmYXAwaTRsOVJxYXhkV093VENpcHR4RUV5aUE9PSIsIm1hYyI6IjlkNzgzMmE2NDE0NWNiNGEyM2I2NjI4OWI4NGIxYzRiYmZlYmNkZGVjMzI2MDJjNDMzYWU2ODBkZGJkMGZlMDgifQ==",
    "origin": "https://shanghaicity.openservice.kankanews.com",
    "referer": "https://shanghaicity.openservice.kankanews.com/public/bus",
    "sec-fetch-dest": "empty",
    "sec-fetch-mode": "cors",
    "sec-fetch-site": "same-origin",
    "User-Agent": "Mozilla/5.0 (Linux; Android 6.0; Nexus 5 Build/MRA58N) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/89.0.4389.90 Mobile Safari/537.36 Edg/89.0.774.63",
    "x-requested-with": "XMLHttpRequest"
}



header_stops = {
    'authority': 'shanghaicity.openservice.kankanews.com',
    'pragma': 'no-cache',
    'cache-control': 'no-cache',
    'upgrade-insecure-requests': '1',
    'user-agent': 'Mozilla/5.0 (Linux; Android 6.0; Nexus 5 Build/MRA58N) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/89.0.4389.90 Mobile Safari/537.36 Edg/89.0.774.54',
    'accept': 'text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.9',
    'sec-fetch-site': 'same-origin',
    'sec-fetch-mode': 'navigate',
    'sec-fetch-user': '?1',
    'sec-fetch-dest': 'document',
    'referer': 'https://shanghaicity.openservice.kankanews.com/public/bus',
    'accept-language': 'zh-CN,zh;q=0.9,en;q=0.8,en-GB;q=0.7,en-US;q=0.6',
    'cookie': 'souid=wKgBHGBYPbFt4k2mkL+PAg==; iwt_uuid=c27e68b4-a6ea-49ec-894f-1bfa3d291374'
}

header_status = {
    'accept': '*/*',
    'accept-encoding': 'gzip, deflate, br',
    'accept-language': 'zh-CN,zh;q=0.9,en;q=0.8,en-GB;q=0.7,en-US;q=0.6',
    'cache-control': 'no-cache',
    'content-type' : 'application/x-www-form-urlencoded',
    'cookie': 'acw_tc=76b20f6a16165668908678691e376c1475a8e3ea4e30081119061755b4e90e; souid=wKgBEWBa2mqiNFaaqSb6Ag==; iwt_uuid=c27e68b4-a6ea-49ec-894f-1bfa3d291374; last_search_records=eyJpdiI6Im9sRTd2T2k5XC9ITkdSWDV1eE1GTmxRPT0iLCJ2YWx1ZSI6Im9jYXdkM1Z3YllJY3NCcmhoRk5aRkx0WHdJZHFScEJoQmVPMnpMUUxYTEk9IiwibWFjIjoiNDVmNmJjNzk4OTJkMWNjNTY3ZTkyMjc0YzQ3NDM1MGI5MDMwNzJmZWE5ZWFkZDc0ZWE0MGNiNGRhMDcwNzc1OSJ9; XSRF-TOKEN=eyJpdiI6IkJDOWJ4eEVQTlhueTlYZGU2UmZ0dmc9PSIsInZhbHVlIjoiUHhxWHM4TVY5RjJncFNickdNeEhZNzFiR0VoY2tLRFVma0xJU1VZQnpPaVkzbTlEVWFyXC94XC9hNU4zZGhzUG43RGV4V2Z1SkZNbWZhU1M1N3dseFB3dz09IiwibWFjIjoiNDc1OWZhODM3MDhlMDg0YTNlOTFmODQzZGIyMGNmODc2ZDVhNjdmNzA5NzZjNzBiYzY3NjZjNDc1MmM4NzRlNCJ9; _session=eyJpdiI6IkxXSHRIaElpeHBBNnVMaFZFcjVra2c9PSIsInZhbHVlIjoiZ01zaGc5eDBHN2ZhQmpFMVRISkxvbnVoMHRxTTZQcHZBSTFhZ2kyYWhyUXJ5VlliazYremdFUGtqY1dkSGI1TzhrSTJxOGF0RHFEOVZpbGxqXC96SDh3PT0iLCJtYWMiOiIxZDFiYTNhNTczNGVlMGJlNGMzNzMyNmQ5ZGQzYmQyNGZiMDE5YzNmMDE2ZDdkNWNiYzcwNDExZTA3NjEyYTVkIn0%3D',
    'origin': 'https://shanghaicity.openservice.kankanews.com',
    'pragma': 'no-cache',
    'referer': 'https://shanghaicity.openservice.kankanews.com/public/bus/mes/sid/',
    'sec-fetch-dest': 'empty',
    'sec-fetch-mode': 'cors',
    'sec-fetch-site': 'same-origin',
    'user-agent': 'Mozilla/5.0 (Linux; Android 6.0; Nexus 5 Build/MRA58N) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/89.0.4389.90 Mobile Safari/537.36 Edg/89.0.774.57',
    'x-requested-with': 'XMLHttpRequest'
}
# @bus_api.route("/bus_sid", methods=['POST'])
# def busSid():
#     req_data = str(request.get_data(), 'utf-8')
#     idnum = json.loads(req_data)['idnum']
#     postData = {'idnum': idnum}
#     postData = urlencode(postData).encode()
#     req = Request(URL, data=postData)
#     resp = urlopen(req)
#     rs = resp.read().decode('utf-8')
#     print(rs)
#     return rs

'''Get bus SID by idnum such as "156路" '''
@bus_api.route("/bus_sid", methods=['POST'])
def busSid():
    req_data = str(request.get_data(), 'utf-8')
    idnum = json.loads(req_data)['idnum']
    params = OrderedDict([("idnum", (None, idnum, 'multipart/form-data'))])
    resp = requests.post(URL_SID, headers=header_stops, data=params)
    print(resp.text)
    return resp.text



'''giving a bus SID, find bus stops list, first and last station
IN: {"busSid":"d91e9c0046829f1b18130b55a2e0876e"}
OUT: 
'''
@bus_api.route("/bus_stops", methods=['POST'])
def busStops():
    reqData = str(request.get_data(), 'utf-8')
    busSid = json.loads(reqData)['busSid']
    resp = requests.get(URL_STOPS+busSid, headers=header_stops)
    #print(resp.text)
    html = etree.HTML(resp.text)
    start = html.xpath('//div[@class="busDirection"]/div[@class="upgoing cur "]/p/span[1]/text()')[0]
    end = html.xpath('//div[@class="busDirection"]/div[@class="upgoing cur "]/p/span[2]/text()')[0]
    #print(start,'->',end)
    nums = html.xpath('//div[@class="station"]/span[1]/text()')
    stops = html.xpath('//div[@class="station"]/span[2]/text()')
    #print(nums)
    #print(stops)
    rs = {
        "start":start,
        "end": end,
        "nums": nums,
        "stops": stops
    }
    return json.dumps(rs)


'''giving a bus SID, Direction, and STOPID, returns bus status'''
def busStatus(busSid, direction, stopId):
    #query bus status
    header_status['referer'] =  header_status['referer'] + busSid
    params = {'stoptype': direction, 'stopid': stopId, 'sid': busSid}
    resp = requests.post(URL_STATUS, headers=header_status, data=params)
    bus_rs = json.loads(resp.text.strip('[').strip(']'))
    if 'error' in bus_rs:
        return '你的公交车还没发车'
    route = bus_rs['@attributes']['cod']
    plate_no = bus_rs['terminal']
    stopdis = bus_rs['stopdis']
    mins = int(int(bus_rs['time'])/60)
    rs = route+'公交车，'+plate_no+'还有'+stopdis+'站，预计'+str(mins)+'分钟后到达。'
    return rs



'''giving a bus SID, Direction, and STOPID, returns bus status
IN: {'direction': '0', 'stopId': '32.', 'busSid': 'd91e9c0046829f1b18130b55a2e0876e'}
OUT: 
{"error":"-2"}
[{"@attributes":{"cod":"961\u8def"},"terminal":"\u6caaA-05261D\u65e0\u969c\u788d","stopdis":"4","distance":"2509","time":"515"}]
'''
@bus_api.route("/bus_status", methods=['POST'])
def busStatusApi():
    reqData = str(request.get_data(), 'utf-8')
    direction = json.loads(reqData)['direction']
    stopId = json.loads(reqData)['stopId']
    busSid = json.loads(reqData)['busSid']
    return busStatus(busSid, direction, stopId)

