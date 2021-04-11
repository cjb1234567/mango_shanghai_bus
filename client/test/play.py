#coding:utf-8
#-*- coding:UTF-8 -*-
import os, sys, subprocess, time, psutil, requests
#from urllib.parse import urlencode

#def getSpeech(phrase):
#    googleAPIurl = "http://fanyi.baidu.com/gettts?lan=zh&spd=3&source=web&text=哈哈哈" 
#    #param = {'text': phrase}
#    #data = urlencode(param)
#    #googleAPIurl += data # Append the parameters
#    return googleAPIurl
 
def raspberryTalk(text): # This will call mplayer and will play the sound
    # subprocess.call(["mplayer",getSpeech(text)], shell=False, stdout=subprocess.PIPE, stderr=subprocess.PIPE)
    # file_path = os.path.join(os.path.dirname(__file__), "yujian.mp3")
    # stdoutData,stderrData = subprocess.Popen(["mplayer",file_path]).communicate()
    # stdoutData,stderrData = subprocess.Popen(["mplayer",getSpeech(text)]).communicate()
    resp = requests.get("http://fanyi.baidu.com/gettts?lan=zh&spd=3&source=web&text="+text, stream=True)
    f = open("speech.mp3", "wb")
    for chunk in resp.iter_content(chunk_size=512):
        if chunk:
            f.write(chunk)
            
    child = subprocess.Popen(["mplayer","speech.mp3"])
    print("continue doing")
    time.sleep(2)
    p = psutil.Process(child.pid)
    p.suspend()
    time.sleep(1)
    print ("pause")
    p.resume()
    print("finished")
if __name__ == "__main__":
    word = '赶紧的，你的公交车要来了,Hurry Up' 
    #urlword = word.decode('utf-8').encode('utf-8')
    #print(urlword)
    raspberryTalk(word)
