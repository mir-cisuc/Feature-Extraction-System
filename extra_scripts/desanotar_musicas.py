# -*- coding: utf-8 -*-
import pandas as pd
from os import listdir
from os.path import isfile, join
from shutil import copyfile 

PATH_MUSICAS = r'C:\Users\Red\Desktop\Investigacao2020\FeatureExtractionSystem\datasets\com_anotacao'
PATH_FINAL_SEM_ANOTACAO = r'C:\Users\Red\Desktop\Investigacao2020\FeatureExtractionSystem\datasets\400_sem_anotacao'
PATH_FINAL_APENAS_REFRAO = r'C:\Users\Red\Desktop\Investigacao2020\FeatureExtractionSystem\datasets\400_chorus_apenas'

def desanotar():
    files = [f for f in listdir(PATH_MUSICAS) if isfile(join(PATH_MUSICAS, f))]

    for f in files:
        simulated_path = PATH_MUSICAS + "\\" + f
        simulated_new_path = PATH_FINAL_SEM_ANOTACAO + "\\desanotated_" + f 
        with open(simulated_path,"r",encoding="utf-8") as ficheiro:
            linhas = ficheiro.readlines()
            new_lines = list()
            for linha in linhas:
                if "[" and "]"  not in linha:
                    new_lines.append(linha)
            with open(simulated_new_path,"w",encoding="utf-8") as new_file:
                for line in new_lines:
                    new_file.write(line)

    # to check if everything is alright
    new_files = [f for f in listdir(PATH_FINAL_SEM_ANOTACAO) if isfile(join(PATH_FINAL_SEM_ANOTACAO, f))]

    counter = 0

    for f in new_files:
        s_n_p = PATH_FINAL_SEM_ANOTACAO + "\\" + f 
        with open(s_n_p,"r",encoding="utf-8") as ficheiro_novo:
            data = ficheiro_novo.read().replace("\n","")
            if "[" or "]" not in data:
                counter +=1
            else:
                print(f)

    if counter == len(new_files):
        print("All files deanotated successfuly")
    else:
        print("Something wrong, %s files are wrong" % (len(new_files) - counter))

def get_chorus_of_song():
    files = [f for f in listdir(PATH_MUSICAS) if isfile(join(PATH_MUSICAS, f))]

    for f in files:
        simulated_path = PATH_MUSICAS + "\\" + f
        simulated_new_path = PATH_FINAL_APENAS_REFRAO + "\\apenas_chorus_" + f 
        with open(simulated_path,"r",encoding="utf-8") as ficheiro:
            linhas = ficheiro.readlines()
            new_lines = list()
            i = 0
            lista_indices = list()
            lista_tuplos = list()
            for i, linha in enumerate(linhas):
                if ("Chorus" or "Hook") in linha:
                    if ("Pre" or "Post") not in linha:
                        if "[" in linha:
                            lista_indices.append(i)
            for k, indice in enumerate(lista_indices):
                while indice < len(linhas):
                    linha = linhas[indice]
                    if linha == '\n':
                        lista_tuplos.append((lista_indices[k],indice))
                        break
                    indice+=1
            
            for i, tuplo in enumerate(lista_tuplos):
                linhas_copiar = linhas[tuplo[0]+1:tuplo[1]]
                for line in linhas_copiar:
                    new_lines.append(line)
                if i +1 != len(lista_tuplos):
                    new_lines.append("\n")
                    

            with open(simulated_new_path,"w",encoding="utf-8") as new_file:
                for line in new_lines:
                    new_file.write(line)



if __name__ == '__main__':
    #desanotar()
    get_chorus_of_song()