# -*- coding: utf-8 -*-
import pandas as pd
from os import listdir
from os.path import isfile, join
from shutil import copyfile



def juntar():

    path_musicas = r'C:\Users\Red\Desktop\Investigacao2020\Utils\Corpus\771\lyrics'
    path_final = r'C:\Users\Red\Desktop\Investigacao2020\FeatureExtractionSystem\datasets\771 songs'
    path_ids = r'C:\Users\Red\Desktop\Investigacao2020\FeatureExtractionSystem\datasets\Quadrantes-771.csv'
    files = [f for f in listdir(path_musicas) if isfile(join(path_musicas, f))]

    dataset_id_quadrant = pd.read_csv(path_ids,sep=',', encoding='utf-8',header=None)
    data_id = dataset_id_quadrant.iloc[:, 0]
    data_quadrant = dataset_id_quadrant.iloc[:, 1]

    contador = 0


    for ficheiro in files:
        nome = ficheiro.split(".txt")[0]
        for elemento in data_id:
            if nome == elemento:
                contador+=1
                copyfile(path_musicas + "\\" + elemento + ".txt",path_final+ "\\" + elemento + ".txt")
                break
    
    print(contador)


if __name__ == '__main__':
    juntar()