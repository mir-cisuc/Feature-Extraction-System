from __future__ import unicode_literals
import youtube_dl
from youtubesearchpython import VideosSearch
import json
import pandas as pd
from os import listdir
from os.path import isfile, join
import os

path_musicas = r'C:\Users\Red\Desktop\Investigacao2020\FeatureExtractionSystem\datasets\dataset_apenas_771\sem_anotacao'
path_audio =  r'C:\Users\Red\Desktop\Investigacao2020\FeatureExtractionSystem\extra_scripts\audio_sem_anotacao'
auxiliar = r'C:\Users\Red\Desktop\Investigacao2020\FeatureExtractionSystem\extra_scripts\auxiliar'


lista_urls = list()

class MyLogger(object):
    def debug(self, msg):
        pass

    def warning(self, msg):
        pass

    def error(self, msg):
        print(msg)


def my_hook(d):
    if d['status'] == 'finished':
        print('Done downloading, now converting ...')


ydl_opts = {
    #'format': 'bestvideo+bestaudio,--merge-output-format',
    'format': 'bestaudio/best',
    'logger': MyLogger(),
    'progress_hooks': [my_hook],
    'outtmpl': auxiliar + r'\%(title)s.%(ext)s',
}

def download_video(url):
    with youtube_dl.YoutubeDL(ydl_opts) as ydl:
        print('Starting the download of %s' % (url))
        ydl.download([url])


#download_video('https://www.youtube.com/watch?v=p6OoY6xneI0')

def get_audio(nome):
    print("Searching for %s" % (nome))
    videosSearch = VideosSearch(nome, limit = 5)

    resultado_pesquisa = videosSearch.result()

    json_string = json.dumps(resultado_pesquisa, indent = 4, ensure_ascii=False)   
    
    #print(json_object)
    json_objeto = json.loads(json_string)

    resultados = json_objeto['result']
    

    json_string_resultados = json.dumps(resultados, indent= 4, ensure_ascii = False)

    lista_resultados = json.loads(json_string_resultados)

    lista_informacao = list()

    for resultado in lista_resultados:
       lista = list()
       print(resultado['title'])
       for key,value in resultado.items():
           # queremos extrair a duracao, as views
           
           if key == 'duration':
               tuplo = (key,value)
               lista.append(tuplo)
           if key == 'viewCount':
               if value['text'] == 'No views':
                   tuplo = ('views',0)
               else:
                   tuplo = ('views',int(value['text'].split(' ')[0].replace(",","")))
               lista.append(tuplo)
           if key == 'link':
               tuplo = ('url',value)
               lista.append(tuplo) 
       lista_informacao.append(lista)

    if len(lista_informacao) != 0:


        #print(key,value)
        media_duracao, lista_final = create_duration_average(lista_informacao)

        sorted_by_views = sorted(lista_informacao, key=sec_elem, reverse= True)


        media_minutos = media_duracao[0]
        media_segundos = media_duracao[1]

        media_em_segundos = media_minutos * 60 + media_segundos

        #print(media_em_segundos)

        for i in range(len(sorted_by_views)):
            # if the duration is similar to the average, we pick that
            link = sorted_by_views[i]
            duracao = link[0][1]
            split = duracao.split(":")
            url = link[2][1]
            total = int(split[0]) * 60 +  int(split[1])

            if (total * 1.2 > media_em_segundos) or (total * 0.8 < media_em_segundos):
                download_video(url)
                global lista_urls
                lista_urls.append(url)
                with open("lista_urls.txt","a") as f_w:
                    f_w.write(nome + " - " + url + "\n")
                print("O %s funciona!!! tempos %s %s" %(i,media_duracao, (split[0],split[1])))
                break
    else:
        with open("lista_urls.txt","a") as f_w:
            f_w.write(nome + " - " + "URL MA\n")


def sec_elem(s):
    return s[1][1]


def create_duration_average(lista):
    lista_final = list()
    for resultado in lista:
        for tuplo in resultado:
            if tuplo[0] == 'duration':
                split = tuplo[1].split(":")
                numero = (int(split[0]),int(split[1]))
                lista_final.append(numero)
    
    #print(lista_final)

    tlist1 = list()

    for tempo in lista_final:
        m1, s1 = numero[0], numero[1]
        # form a list of centisecond values
        tlist1.append(m1*60*100)
        tlist1.append(s1*100)
    # get the total centiseconds
    centis = sum(tlist1) / len(lista_final)
    # take integer average
    #centis = centis // 2
    # get minutes, seconds from centiseconds
    seconds, centis = divmod(centis, 100)
    minutes, secs = divmod(seconds, 60)

    tuplo = (minutes,secs)

  
    print("Average time = %02d:%02d" % (minutes, secs))

    return tuplo, lista_final 

def download_songs():
    with open("urls.txt","r") as f:
        for line in f:
            download_video(line.strip())


'''def get_names():
    files_lirica = [path_musicas + "\\" + f for f in listdir(path_musicas) if isfile(join(path_musicas, f))]
    files_audio = [path_audio + "\\" + f for f in listdir(path_audio) if isfile(join(path_audio, f))]
    
    for files in files_lirica:
        print(files.split(os.sep)[-1].replace(".txt",""))'''
lista = list()

'''with open("lista_urls.txt","r") as f:
    for line in f:
        lista.append(line.split(" - ")[-1].strip())

with open("urls.txt","w") as f_w:
    for linha in lista:
        f_w.write(linha + "\n")'''

if __name__ == '__main__':
    download_songs()        
#get_names()
