#!/usr/bin/python3
from flask import Blueprint, request
from urllib.parse import urlencode
from urllib.request import urlopen
from urllib.request import Request
from urllib.error import URLError
import json
import time
import uuid
import requests

timer = time.perf_counter

voice_api = Blueprint('voice_api', __name__)

API_KEY = 'asNaa4t2roz6lh5QsqpgwXVG'
SECRET_KEY = '601af77bf44c84ffe088ba65a9407412'
FORMAT = 'wav'

mac = uuid.UUID(int=uuid.getnode()).hex[-12:].upper()
CUID = '%s:%s:%s:%s:%s:%s' % (mac[0:2], mac[2:4], mac[4:6], mac[6:8], mac[8:10], mac[10:])

# 采样率
RATE = 16000  # 固定值
# 产品编号
DEV_PID = 80001  # 固定值
SCOPE = 'audio_voice_assistant_get'  # 有此scope表示有asr能力，没有请在网页里勾选，非常旧的应用可能没有

# 获取TOKEN地址
TOKEN_URL = 'http://openapi.baidu.com/oauth/2.0/token'
# 语音识别地址
ASR_URL = 'https://vop.baidu.com/pro_api'
# 文字转语音地址
T2V_URL = 'https://tsn.baidu.com/text2audio'
# 天气地址
WTHER_URL = 'http://api.map.baidu.com/weather/v1/?district_id=310104&data_type=all&ak=ZKpZpWp1GtaWaIxu5OPldcMq'

# 公交车状态地址
BUS_URL = 'http://127.0.0.1:5000/bus_status'


# 获取token
def fetch_token():
    params = {'grant_type': 'client_credentials',
              'client_id': API_KEY,
              'client_secret': SECRET_KEY}
    post_data = urlencode(params)
    post_data = post_data.encode('utf-8')
    req = Request(TOKEN_URL, post_data)
    try:
        f = urlopen(req)
        result_str = f.read()
    except URLError as err:
        print('token http response http code : ' + str(err.code))
        result_str = err.read()
    result_str = result_str.decode()

    print(result_str)
    result = json.loads(result_str)
    print(result)
    if 'access_token' in result.keys() and 'scope' in result.keys():
        print(SCOPE)
        print('SUCCESS WITH TOKEN: %s  EXPIRES IN SECONDS: %s' % (result['access_token'], result['expires_in']))
        return result['access_token']
    else:
        print('MAYBE API_KEY or SECRET_KEY not correct: access_token or scope not found in token response')


# 语音转文字
def speech_recognize(voice_file, file_lenth):
    token = fetch_token()
    params = {'dev_pid': DEV_PID,
              # "lm_id" : LM_ID,    #测试自训练平台开启此项
              'format': FORMAT,
              'rate': RATE,
              'token': token,
              'cuid': CUID,
              'channel': 1,
              'speech': voice_file,
              'len': file_lenth
              }
    post_data = json.dumps(params, sort_keys=False)
    # print post_data
    req = Request(ASR_URL, post_data.encode('utf-8'))
    req.add_header('Content-Type', 'application/json')
    try:
        begin = timer()
        f = urlopen(req)
        result_str = f.read()
        print("Request time cost %f" % (timer() - begin))
    except URLError as err:
        print('asr http response http code : ' + str(err.code))
        result_str = err.read()
    result_str = str(result_str, 'utf-8')
    # print(result_str)
    result = json.loads(result_str)['result'][0]
    return result


# 天气
def weather(day):
    text_day = ['今天', '明天', '后天']
    resp = requests.get(WTHER_URL)
    rs = json.loads(resp.text)
    loc = rs['result']['location']['city']+rs['result']['location']['name']
    desc1 = rs['result']['forecasts'][day]['text_day']
    desc2 = rs['result']['forecasts'][day]['text_night']
    temp1 = str(rs['result']['forecasts'][day]['high'])
    temp2 = str(rs['result']['forecasts'][day]['low'])
    wind1 = rs['result']['forecasts'][day]['wc_day']
    sum_rs = loc+'，'+text_day[day]+'白天'+desc1+'，晚上'+desc2+'，'+temp1+'到'+temp2+'度，风力'+wind1
    return sum_rs


# 公交车状态
def bus_status():
    params = {'direction': '0', 'stopId': '32.', 'busSid': 'd91e9c0046829f1b18130b55a2e0876e'}
    resp = requests.post(BUS_URL, json=params)
    return resp.text


# 时间
def get_time():
    now = time.localtime()
    hh = now.tm_hour
    mm = now.tm_min
    return '现在是'+str(hh)+'点'+str(mm)+'分'


# 星期
def get_weekday():
    w = time.localtime().tm_wday+1
    if w == 7:
        w = '天'
    return '今天是星期'+str(w)


# 功能-关键词映射字典
functions = {
    '今天&天气': 'weather(0)',
    '明天&天气': 'weather(1)',
    '后天&天气': 'weather(2)',
    '天气': 'weather(0)',
    '车&多久|到了没|来了没|来了吗': 'bus_status()',
    '几点': 'get_time()',
    '今天&星期|礼拜': 'get_weekday()'
}


# 输入问题, 获取答案
def get_answer(question):
    match_count = 0
    for pattern in functions.keys():
        # 先按&(and)拆分，再按|（or）拆分匹配
        for words in pattern.split("&"):
            for word in words.split('|'):
                if word in question:
                    match_count = match_count + 1
                    break  # words中只需匹配到任意一次，就不再继续匹配

        # 判断匹配中的次数与要求一致，则执行对应功能
        if match_count == len(pattern.split("&")):
            return eval(functions[pattern])
        else:
            match_count = 0 # 匹配次数清零
    return '哎呀，你说的太好了，我竟无以对答'


# 语音转文字功能API
@voice_api.route("/voice2text", methods=['POST'])
def voice2text():
    req_data = str(request.get_data(), 'utf-8')
    speech = json.loads(req_data)['speech']
    length = json.loads(req_data)['len']
    return speech_recognize(speech, length)


# 语音对话API
# 输入语音， 转换成文字， 得到文字回复
@voice_api.route("/conversation", methods=["POST"])
def conversation():
    # 输入语音
    req_data = str(request.get_data(), 'utf-8')
    speech = json.loads(req_data)['speech']
    length = json.loads(req_data)['len']

    # 转换成文字
    text_in = speech_recognize(speech, length)
    print(text_in)

    # 获取文字回复
    text_out = get_answer(text_in)
    return text_out

# 文字输入对话API
# 输入文字， 转换成文字， 得到文字回复
@voice_api.route("/text_conversation", methods=["POST"])
def text_conversation():
    # 输入语音
    req_data = str(request.get_data(), 'utf-8')
    text_in = json.loads(req_data)['text_in']

    # 获取文字回复
    text_out = get_answer(text_in)
    return text_out


# 接口测试入口
@voice_api.route("/test", methods=['GET'])
def test():
    return str(weather(0))
