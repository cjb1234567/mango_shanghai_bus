#!/usr/bin/python3
from flask import Flask, request, render_template
from voiceAPI import voice_api
from busAPI import bus_api

from flask_cors import CORS

app = Flask(__name__)
CORS(app, supports_credentials=True)

app.register_blueprint(voice_api)
app.register_blueprint(bus_api)

@app.route('/')
def index():
    list1 = list(range(10))
    my_list = [{"id": 1, "value": "我爱工作"},
               {"id": 2, "value": "工作使人快乐"},
               {"id": 3, "value": "沉迷于工作无法自拔"},
               {"id": 4, "value": "日渐消瘦"},
               {"id": 5, "value": "以梦为马，越骑越傻"}]
    return render_template(
        # 渲染模板语言
        'index.html',
        title='hello world',
        list2=list1,
        my_list=my_list
    )


# step1 定义过滤器
def do_listreverse(li):
    temp_li = list(li)
    temp_li.reverse()
    return


# step2 添加自定义过滤器
app.add_template_filter(do_listreverse, 'listreverse')



if __name__ == '__main__':
    app.run()
