# -*- coding: utf-8 -*-
import pandas as pd
from os import listdir
from os.path import isfile, join
from shutil import copyfile 

PATH_FINAL_REFRAO_ANOTADO = r'C:\Users\Red\Desktop\Investigacao2020\FeatureExtractionSystem\datasets\400_chorus_apenas'
PATH_FINAL_SCRIPT_CHORUS = r'C:\Users\Red\Desktop\Investigacao2020\FeatureExtractionSystem\datasets\script_400'

class Letra:
    def __init__(self, lista_blocos):
        self.lista_blocos = lista_blocos

    def __str__(self):
        string = ''
        for k, bloco in enumerate(self.lista_blocos):
            for i, letra in enumerate(bloco):
                string += letra + "\n"
                    
            if k != len(self.lista_blocos) -1:
                string += "\n"
        return string


    def get_blocos(self):
        return self.lista_blocos


def comparar():
    files_anotados = [f for f in listdir(PATH_FINAL_REFRAO_ANOTADO) if isfile(join(PATH_FINAL_REFRAO_ANOTADO, f))]
    files_script = [f for f in listdir(PATH_FINAL_SCRIPT_CHORUS) if isfile(join(PATH_FINAL_SCRIPT_CHORUS, f))]

    if len(files_anotados) != len(files_script):
        print("Len anotados %d, Len script %d" %(len(files_anotados),len(files_script))) 
        exit()

    tamanho = len(files_anotados)

    lista_acertos = list()

    contador = 0

    for i in range(tamanho):
    
        ficheiro_anotado = files_anotados[i]
        ficheiro_script = files_script[i]

        print(ficheiro_script)


        simulated_anotado = PATH_FINAL_REFRAO_ANOTADO + '\\' + ficheiro_anotado
        simulated_script = PATH_FINAL_SCRIPT_CHORUS + '\\' + ficheiro_script

        conteudo_anotado = open(simulated_anotado,"r",encoding="utf-8").read().split("\n")
        conteudo_script = open(simulated_script,"r",encoding="utf-8").read().split("\n")

        lista_blocos_anotado = create_block(conteudo_anotado)
        
        lista_blocos_script = create_block(conteudo_script)

        nr_blocos = 0


        for bloco in lista_blocos_script.get_blocos():
            for bloco_comparar in lista_blocos_anotado.get_blocos():
                if compare_block(bloco,bloco_comparar):
                    nr_blocos+=1
                    #print_block(bloco)
                    #print("-"*50)
                    #print_block(bloco_comparar)
                    #print("-"*100)
                    break

        #print("%d = %s\n" % ( nr_blocos,ficheiro_anotado))
        #if i == 5:
            #exit()
    
        if nr_blocos == len(lista_blocos_anotado.get_blocos()):
            contador +=1
    print(contador)

def print_block(lista):
    for a in lista:
        print(a)


def create_block(lista):
    lista_aux = list()
    i = 0
    while i < len(lista):
        bloco_auxiliar = list()
        while i < len(lista) and lista[i] != '':
            bloco_auxiliar.append(lista[i])
            i+=1
        lista_aux.append(bloco_auxiliar)
        i+=1


    return Letra(lista_aux)


def compare_block(bloco_1,bloco_2):
    if len(bloco_1) != len(bloco_2):
        return False
    else:
        for i in range(len(bloco_1)):
            if bloco_1[i] != bloco_2[i]:
                return False
    return True


if __name__ == '__main__':
    #desanotar()
    comparar()