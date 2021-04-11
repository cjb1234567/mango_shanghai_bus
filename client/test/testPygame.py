# -*- coding: utf-8 -*-
import pygame.mixer,os,sys,time

pygame.init()
pygame.mixer.init()
print("Play Music")
screen=pygame.display.set_mode([100,100])

print(os.path.dirname(__file__))
#当前文件夹路径
musicDir = os.path.join(os.path.dirname(__file__), "music")
#music文件夹下的文件列表
musicList = os.listdir(musicDir)
print("musicList")
#遍历播放
for file in range(0, len(musicList)):
    filePath = os.path.join(musicDir, musicList[file])
    if os.path.isfile(filePath):
        print("filePath")
        pygame.mixer.music.load(filePath)
        #播放
        pygame.mixer.music.play()
        time.sleep(5)
        #暂停播放
        #淡出后退出
        pygame.mixer.music.fadeout(3000)
        #标识暂停位置
        mark_pos = pygame.mixer.music.get_pos() * 0.001
        pygame.time.wait(3000)
        #从标志位开始播放
        pygame.mixer.music.play(0, mark_pos)
        #继续播放
        # print "pygame.mixer.music.unpause"
        # pygame.mixer.music.unpause()
        # pygame.mixer.music.play()
        time.sleep(15)
        
    
# while 1:
    # for event in pygame.event.get():
        # if event.type==pygame.QUIT:
        
            # sys.exit()
