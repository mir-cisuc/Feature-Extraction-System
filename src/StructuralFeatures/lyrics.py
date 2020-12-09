import lyricsgenius
import pandas as pd

CLIENT_ID = 'c1zeZ5TbN4y5Tq0zuSox-U2ngikWCQLbSr9zko_f8RDhfIK9gk9a9O1GrjYrAXZi'
CLIENT_SECRET = '3ZyQIm4qz0DMI7ifc4fmqWcmI4R2JtYq2JfqTclcWImx0SRvuPmsIklSiTJ6wIAT4mBXdVQqwaXi95DwzwcZeg'

ACCESS_TOKEN = 'OEc2PR7u9yuG6dLJEVVB0J1qYyUbT97l0gGxYomMaOT8QhZHvvfkS24y05RWWAlG'

FICHEIRO_INPUT = 'lista_musicas.csv'

DIRETORIA_BOA = 'com_anotacao/'
DIRETORIA_SEM_LETRA = 'sem_letra/'
DIRETORIA_SEM_ANOTACAO = 'sem_anotacao/'

CONTADOR_ANOTACOES = 0


def write_to_File(nome_musica, letra):
    diretoria = ''

    if 'Verse 1' in letra or '[Chorus]' in letra or 'Chorus:' in letra:
        global CONTADOR_ANOTACOES
        CONTADOR_ANOTACOES +=1
        diretoria = DIRETORIA_BOA 
    else:
        diretoria = DIRETORIA_SEM_ANOTACAO

    f = open(diretoria + nome_musica + ".txt", "w", encoding="utf-8")
    f.write(letra)
    f.close()


def ler_musica_genius(nome_musica):
    song = genius.search_song(nome_musica)

    if song != None:
         write_to_File(nome_musica,song.lyrics)
    else:
        f = open(DIRETORIA_SEM_LETRA + nome_musica + ".txt", "w", encoding="utf-8")
        f.write("")
        f.close()

def ler():
    data_total = pd.read_csv(FICHEIRO_INPUT,sep=',', encoding='utf-8')
    
    for i in range(0,len(data_total)):
        if i!= 334 and i != 476 and i!= 598 and i!=707: 
            string = data_total['Artist'][i] + " " + data_total['Title'][i]
            string = string.replace("_"," ")
            string = string.replace("/"," ")
            string = string.replace("?"," ")
            ler_musica_genius(string)
            print(i)

if __name__ == '__main__':
    genius = lyricsgenius.Genius(ACCESS_TOKEN)
    ler()

    print("Numero de musicas com anotacao {}" .format(CONTADOR_ANOTACOES))














