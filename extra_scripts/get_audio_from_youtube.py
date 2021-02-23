from __future__ import unicode_literals
import youtube_dl
from youtubesearchpython import VideosSearch
import json


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
}

def download_video(url):
    with youtube_dl.YoutubeDL(ydl_opts) as ydl:
        print('Starting the downloads')
        ydl.download([url])


#download_video('https://www.youtube.com/watch?v=p6OoY6xneI0')

def get_video(nome):
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
       for key,value in resultado.items():
           # queremos extrair a duracao, as views
           if key == 'duration':
               tuplo = (key,value)
               lista.append(tuplo)
           if key == 'viewCount':
               tuplo = ('views',int(value['text'].split(' ')[0].replace(",","")))
               lista.append(tuplo)
           if key == 'link':
               tuplo = ('url',value)
               lista.append(tuplo) 
       lista_informacao.append(lista)

    
           #print(key,value)
    media_duracao, lista_final = create_duration_average(lista_informacao)

    sorted_by_views = sorted(lista_informacao, key=sec_elem, reverse= True)


    media_minutos = media_duracao[0]
    media_segundos = media_duracao[1]

    media_em_segundos = media_minutos * 60 + media_segundos

    print(media_em_segundos)

    for i in range(len(sorted_by_views)):
        # if the duration is similar to the average, we pick that
        link = sorted_by_views[i]
        duracao = link[0][1]
        split = duracao.split(":")
        total = int(split[0]) * 60 +  int(split[1])

        if (total * 1.2 > media_em_segundos) or (total * 0.8 < media_em_segundos):
            print("0 %s funciona!!! tempos %s %s" %(i,media_duracao, (split[0],split[1])))
            break


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
    
    print(lista_final)

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

    tuplo = (minutes,seconds)

  
    print("Average time = %02d:%02d" % (minutes, secs))

    return tuplo, lista_final 



    

get_video('why not loona audio')