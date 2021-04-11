# detect.py
import os
import base64
import json
import pyaudio
import pygame
import requests
import wave
import time
import datetime
import numpy as np
from pocketsphinx import LiveSpeech
from multiprocessing import Process
import psutil

CHUNK = 1024  # 每个缓冲区的帧数
FORMAT = pyaudio.paInt16  # 采样位数
CHANNELS = 1  # 单声道
RATE = 16000  # 采样频率

API_KEY = 'asNaa4t2roz6lh5QsqpgwXVG'
SECRET_KEY = '601af77bf44c84ffe088ba65a9407412'

# 获取TOKEN地址
TOKEN_URL = 'http://openapi.baidu.com/oauth/2.0/token'
# 文字转语音地址
T2V_URL = 'https://tsn.baidu.com/text2audio'
# 文字转语音地址
T2V_URL = 'https://tsn.baidu.com/text2audio'

# Conversation 语音对话地址
CON_URL = 'http://127.0.0.1:5000/conversation'

# Text Conversation 文字对话地址
T_CON_URL = 'http://127.0.0.1:5000/text_conversation'

# 公交车提醒地址
BUS_ALARM_URL = 'http://192.168.43.145:9090/bus/get_audio/text'

def record_audio(wave_out_path, max_record_second):
    """ 录音功能 """
    p = pyaudio.PyAudio()  # 实例化对象
    stream = p.open(format=FORMAT,
                    channels=CHANNELS,
                    rate=RATE,
                    input=True,
                    frames_per_buffer=CHUNK)  # 打开流，传入响应参数
    less = []
    frames = []
    for i in range(0, int(RATE / CHUNK * max_record_second)): # 最长录制时间（秒）
        print(datetime.datetime.now().strftime('%Y-%m-%d %H:%M:%S.%f')+" recording")
        data = stream.read(CHUNK)
        frames.append(data)
        # 检查音量是否大于阈值
        volume = np.max(np.fromstring(data, dtype=np.short))
        if volume < 1500:
            less.append(-1)
            print("below threshold, counting: ", less)
            # 如果有连续15个循环的点，都不是声音信号，就认为音频结束了
            if len(less) == 5:
                break
        else:
            less = []
    stream.stop_stream()  # 关闭流
    stream.close()
    p.terminate()
    print('over')

    # 保存为wav文件
    wf = wave.open(wave_out_path, 'wb')  # 打开 wav 文件。
    wf.setnchannels(CHANNELS)  # 声道设置
    wf.setsampwidth(p.get_sample_size(FORMAT))  # 采样位数设置
    wf.setframerate(RATE)  # 采样频率设置
    wf.writeframes(b''.join(frames))
    wf.close()
    print('writen')


# def play_audio(wave_input_path):
#     p = pyaudio.PyAudio()  # 实例化
#     wf = wave.open(wave_input_path, 'rb')  # 读 wav 文件
#     stream = p.open(format=p.get_format_from_width(wf.getsampwidth()),
#                     channels=wf.getnchannels(),
#                     rate=wf.getframerate(),
#                     output=True)
#     data = wf.readframes(CHUNK)  # 读数据
#     while len(data) > 0:
#         stream.write(data)
#         data = wf.readframes(CHUNK)
#
#     stream.stop_stream()  # 关闭资源
#     stream.close()
#     p.terminate()


# 准备发送语音
def prepare_params(voice_file):
    # 读取文件二进制内容
    f_obj = open(voice_file, 'rb')
    content = base64.b64encode(f_obj.read())  # 转base64编码格式
    speech = content.decode("utf-8")
    size = os.path.getsize(voice_file)

    # json封装
    data = json.dumps({
        'speech': speech,
        'len': size
    })
    return data


def fetch_token():
    params = {'grant_type': 'client_credentials',
              'client_id': API_KEY,
              'client_secret': SECRET_KEY}
    resp = requests.post(TOKEN_URL, params)

    # print(resp.text)
    result = json.loads(resp.text)
    # print(result)
    if 'access_token' in result.keys():
        return result['access_token']
    else:
        print('MAYBE API_KEY or SECRET_KEY not correct: access_token or scope not found in token response')


# 文字转语音
def text2mp3(text):
    token = fetch_token()
    length = len(text)
    if length == 0:
        text = '什么？'
    text = requests.utils.quote(text)
    params = {'tex': text,
              'tok': token,
              'cuid': '1111',
              'ctp': 1,
              'lan': 'zh',
              'spd': 5,
              'pit': 5,
              'vol': 5,
              'per': 4
              }
    header = {
        'Content-Type': 'application/x-www-form-urlencoded'
    }
    # print post_data
    res = requests.post(T2V_URL, data=params, headers=header)

    if not isinstance(res, dict):
        with open('music/text2voice.mp3', 'wb') as f:
            for chunk in res.iter_content(chunk_size=1024):
                if chunk:
                    f.write(chunk)
    return 'music/text2voice.mp3'


# 发送声音到云端
def send_voice(voice_file):
    # 读取文件二进制内容
    f_obj = open(voice_file, 'rb')
    content = base64.b64encode(f_obj.read())  # 转base64编码格式
    speech = content.decode("utf-8")
    size = os.path.getsize(voice_file)

    # json封装
    params = json.dumps({
        'speech': speech,
        'len': size
    })
    # 发送请求
    resp = requests.post(CON_URL, data=params)

    # 返回结果
    return resp.text


def play_mp3(file):
    pygame.mixer.pre_init(16000, -16, 2, 2048)
    pygame.mixer.init()
    pygame.mixer.music.load(file)
    pygame.mixer.music.play(loops=0, start=0.0)
    while pygame.mixer.music.get_busy():
        time.sleep(1)
    pygame.mixer.music.unload()


# 根据硬件客户端sno查询闹钟事件
def alarm():
    #查询事件

    # json封装
    # params = json.dumps({'text_in': '我的公交车还有多久到'})

    # 发送请求
    resp = requests.get(BUS_ALARM_URL)

    # 返回结果
    return resp.text


# 闹钟
def alarm_timer():
    loop = 0
    while True:
        cur_time = list(time.localtime())
        loop = loop + 1
        print('alarm-is-working:'+str(loop))
        # if cur_time[3] == 22 and cur_time[4] >= 30 and cur_time[4] <= 50: # compare alarm setting time
        #     rs = alarm('bmango')
        #     print(rs)
        #     play_mp3(text2mp3(rs))
        alarm_rs = alarm()
        if alarm_rs != '':
            alarm_mp3 = text2mp3(alarm_rs)
            play_mp3(alarm_mp3)
        time.sleep(30)


if __name__ == "__main__":
    # Start alarm
    ala = Process(target=alarm_timer, args=())
    ala.start()
    print('start a process.')
    pause = psutil.Process(ala.pid)
    # Start LiveSpeech
    speech = LiveSpeech(lm=False, keyphrase='mango', kws_threshold=1e-20)
    for phrase in speech:
        # 替换唤醒词，防止被重复唤醒
        speech = ''
        # 停止闹钟
        if pause.status() == 'running':
            pause.suspend()
        print(pause.status())
        # 快速回应
        print("user:", phrase)
        print("speaker:来了")
        play_mp3("music/nishuoba.mp3")
        # 人类反应时间
        # time.sleep(0.3)

        # 记录提问语音
        record_audio("music/comm.wav", 5)

        # 获取答案，转换为语音
        answer_text = send_voice("music/comm.wav")
        answer_mp3 = text2mp3(answer_text)

        # 播放答案语音
        play_mp3(answer_mp3)
        # time.sleep(2)

        # 恢复唤醒词
        speech = LiveSpeech(lm=False, keyphrase='mango', kws_threshold=1e-20)

        # 继续闹钟
        if pause.status() == "stopped":
            pause.resume()


