# -*- coding: utf-8 -*-
import pandas as pd
from os import listdir
from os.path import isfile, join
from shutil import copyfile 
import numpy as np

PATH_FINAL_APENAS_REFRAO= r'C:\Users\Red\Desktop\Investigacao2020\FeatureExtractionSystem\datasets\400_chorus_apenas'
PATH_FINAL_SEM_ANOTACAO = r'C:\Users\Red\Desktop\Investigacao2020\FeatureExtractionSystem\datasets\400_sem_anotacao'
PATH_FINAL_TOTAL_ANOTADO = r'C:\Users\Red\Desktop\Investigacao2020\FeatureExtractionSystem\datasets\com_anotacao'
PATH_FINAL_SCRIPT_CHORUS = r'C:\Users\Red\Desktop\Investigacao2020\FeatureExtractionSystem\datasets\script_400'
FILE_INDEXS_CHORUS_APENAS = r'C:\Users\Red\Desktop\Investigacao2020\FeatureExtractionSystem\datasets\file_indexs_chorus_apenas.txt'
FILE_INDEXS_SCRIPT_CHORUS = r'C:\Users\Red\Desktop\Investigacao2020\FeatureExtractionSystem\datasets\file_indexs_script_chorus.txt'
PATH_IDS = r'C:\Users\Red\Desktop\Investigacao2020\FeatureExtractionSystem\datasets\Quadrantes-771.csv'
PATH_MUSICAS_TODAS = r'C:\Users\Red\Desktop\Investigacao2020\Utils\Corpus\771\lyrics'
PATH_MUSICAS_FINAIS = r'C:\Users\Red\Desktop\Investigacao2020\FeatureExtractionSystem\datasets\771 songs'

class Letra:
    def __init__(self, lista_blocos):
        self.lista_blocos = lista_blocos

    def __str__(self):
        string = ''
        for k, bloco in enumerate(self.lista_blocos):
            for i, letra in enumerate(bloco):
                string += str(i) + " - " + letra + "\n"
                    
            if k != len(self.lista_blocos) -1:
                string += "\n"
        return string


    def get_blocos(self):
        return self.lista_blocos

#funcao que dada um path de musicas com anotacao, vai criar para cada ficheiro um ficheiro novo mas sem as anotacao, tendo em conta
#que remove os \n excessivos para ficar tudo direito
def desanotar_musicas(): 
    files = [f for f in listdir(PATH_FINAL_TOTAL_ANOTADO) if isfile(join(PATH_FINAL_TOTAL_ANOTADO, f))]

    for f in files:
        simulated_path = PATH_FINAL_TOTAL_ANOTADO + "\\" + f
        simulated_new_path = PATH_FINAL_SEM_ANOTACAO + "\\desanotated_" + f 
        with open(simulated_path,"r",encoding="utf-8") as ficheiro:
            linhas = ficheiro.readlines()
            new_lines = list()
            for linha in linhas:
                if "[" and "]"  not in linha: # se a linha nao contiver anotacoes
                    new_lines.append(linha)
            if new_lines[0] == '\n': # remove the case where the first line is a chorus
                new_lines.pop(0)

            lista_indexs = list()

            for i,frase in enumerate(new_lines): # para o caso em que temos ficheiros com varios \n seguidos
                if frase == '\n':
                    if i +1 != len(new_lines):
                        if new_lines[i+1] == '\n':
                            lista_indexs.append(i)
            
            #print(f)
            for index in lista_indexs: # remove the cases where we have two \n in a row (or more)
                new_lines.pop(index)
                for i in range(len(lista_indexs)):
                    lista_indexs[i] = lista_indexs[i] - 1
            
            
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
            if "[" or "]" not in data: # vamos verificar se por acaso ficou algum parantesis em algum ficheiro
                counter +=1
            else:
                print(f)

    if counter == len(new_files):
        print("All files deanotated successfuly")
    else:
        print("Something wrong, %s files are wrong" % (len(new_files) - counter))

# dado uma pasta com as musicas anotadas vamos criar um pasta com todos os ficheiros novos em que so temos o refrao da musica
def get_chorus_of_song():
    files = [f for f in listdir(PATH_FINAL_TOTAL_ANOTADO) if isfile(join(PATH_FINAL_TOTAL_ANOTADO, f))]

    for f in files:

        simulated_path = PATH_FINAL_TOTAL_ANOTADO + "\\" + f
        simulated_new_path = PATH_FINAL_APENAS_REFRAO + "\\apenas_chorus_" + f 
        with open(simulated_path,"r",encoding="utf-8") as ficheiro:
            linhas = ficheiro.readlines()
            new_lines = list()
            i = 0
            lista_indices = list()

            lista_tuplos = list()
            for i, linha in enumerate(linhas):               
                if "Chorus" in linha or "Hook" in linha:    
                    #print(linha)                
                    if "Pre" not in linha: # para o caso de Pre-Chorus
                       # print(linha) 
                        if "Post" not in linha: # para o casto de Post-Chorus
                            #print(linha) 
                            if "[" in linha: # pode haver o caso de "Chorus" estar na letra e nao ser anotacao
                                #print(linha.strip())
                                lista_indices.append(i)
            for k, indice in enumerate(lista_indices): # depois vamos buscar os indices para sabermos o guardar
                while indice < len(linhas):
                    linha = linhas[indice]
                    if linha == '\n' or indice +1 == len(linhas):
                        if indice+1 == len(linhas):
                            lista_tuplos.append((lista_indices[k],indice+1))
                        else:
                            lista_tuplos.append((lista_indices[k],indice))
                        break
                    indice+=1

            # vamos guardar as linhas que queremos copiar num ficheiro novo
            for i, tuplo in enumerate(lista_tuplos):
                linhas_copiar = linhas[tuplo[0]+1:tuplo[1]]
                for line in linhas_copiar:
                    new_lines.append(line)
                if i +1 != len(lista_tuplos):
                    new_lines.append("\n")
                    

            with open(simulated_new_path,"w",encoding="utf-8") as new_file:
                for line in new_lines:
                    new_file.write(line)


def get_indices():
    files_total_anotados = [f for f in listdir(PATH_FINAL_TOTAL_ANOTADO) if isfile(join(PATH_FINAL_TOTAL_ANOTADO, f))]

    print(len(files_total_anotados))

    #ficheiro_total_anotado = 'Alice in Chains Brother.txt'
    #ficheiro_total_anotado = '50 Cent All of Me.txt'
    for k, ficheiro_total_anotado in enumerate(files_total_anotados):  
        simulated_total_anotado = PATH_FINAL_TOTAL_ANOTADO + '\\' + ficheiro_total_anotado  

        conteudo_total_anotado = open(simulated_total_anotado,"r",encoding="utf-8").read().split("\n")

        lista_blocos_anotado = create_letra(conteudo_total_anotado)

        #print(ficheiro_total_anotado)

        lista_indices = list()


        for i in range(len(lista_blocos_anotado.get_blocos())):
            if isChorus(i,lista_blocos_anotado):
                #print(conteudo_total_anotado[i])
                lista_indices.append(i)
        
        if (len(lista_indices)) == 0:
            print(ficheiro_total_anotado)

        with open(FILE_INDEXS_CHORUS_APENAS,"a") as f:
            for i, indice in enumerate(lista_indices):
                f.write(str(indice))
                if i + 1 != len(lista_indices):
                    f.write(',')
                else:
                    if k +1 != len(files_total_anotados):
                        f.write('\n')

def isChorus(indice,conteudo):
    for k, bloco in enumerate(conteudo.get_blocos()):
        if k == indice:
            for linha in bloco:
                if "Chorus" in linha or "Hook" in linha:                    
                    if "Pre" not in linha:
                        if "Post" not in linha:
                            if "[" in linha:
                                return True
    return False

def compare_index_files():
    '''- sao iguais (chorus oficial = chorus correto)
    - sao completamente diferentes (tudo chorus errado)
    - acerta todos e tem a mais (tem mais chorus do que é oficial, chorus a mais)
    - acerta alguns e tem a menos (tem menos chorus do que oficial, chorus a menos)'''

    linhas_chorus_apenas = open(FILE_INDEXS_CHORUS_APENAS,"r").read().split("\n")
    linhas_script_chorus = open(FILE_INDEXS_SCRIPT_CHORUS,"r").read().split("\n")

    if (len(linhas_chorus_apenas) != len(linhas_script_chorus)):
        print("Alguma coisa correu mal, len chorus apenas %s, len script chorus %s"  % (len(linhas_chorus_apenas),len(linhas_script_chorus)))
        exit()

    tamanho = len(linhas_chorus_apenas)

    print(tamanho)

    #resultados = [[0] * 5] * tamanho
    resultados = np.zeros((tamanho,5)).astype(int)

    for i in range(tamanho):
        lista_chorus_apenas = list(linhas_chorus_apenas[i].split(",")) 
        lista_script_chorus = list(linhas_script_chorus[i].split(",")) 

        

        nr_refroes_correto = len(lista_chorus_apenas)
        nr_refroes_detetados = len(lista_script_chorus)

        resultados[i][4] = nr_refroes_correto

        for k in range(len(lista_chorus_apenas)):
            lista_chorus_apenas[k] = int(lista_chorus_apenas[k])

        for k in range(len(lista_script_chorus)):
            lista_script_chorus[k] = int(lista_script_chorus[k])

        #print(lista_chorus_apenas,lista_script_chorus)

        if lista_script_chorus == lista_chorus_apenas: # caso em que temos direct match  
            resultados[i][0] = nr_refroes_detetados
        # print(str(i) + " 1 - " +  str(resultados[i]))
            continue

        # caso em que todos os valores sao diferentes
        saoTotalmenteDiferentes = True

        for valor_apenas_chorus in lista_chorus_apenas:
            if valor_apenas_chorus in lista_script_chorus:
                #print(valor_apenas_chorus)
                saoTotalmenteDiferentes = False
            # print(str(i) + " 2 - " +  str(resultados[i]))
                break
        
        if saoTotalmenteDiferentes: #temos dois arrays completamente diferentes
            #print("oi xd")
            resultados[i][1] = nr_refroes_detetados 
        # print(str(i) + " 2 - " +  str(resultados[i]))
            continue

        # para chegar aqui, tem que ter pelo menos 1 valor igual, senao tinha entrado em cima
        dif_1 = list(set(lista_script_chorus) - set(lista_chorus_apenas))
        dif_2 = list(set(lista_chorus_apenas) - set(lista_script_chorus))

        detetado_a_menos = len(dif_2) # chorus menos (resultados[i][3])
        detetado_a_mais = len(dif_1) # chorus mais (resultados[i][2])

        certos = nr_refroes_detetados - detetado_a_mais

        resultados[i][0] = certos
        resultados[i][2] = detetado_a_mais
        resultados[i][3] = detetado_a_menos

        #print(resultados[i])

        #print(str(i) + " 3 - " +  str(resultados[i]))
        #continue

    #print(resultados[0],resultados[50])
    #print(resultados)
    for i in range(len(resultados)):
        print("%s = %s " % (i+2,resultados[i]))
            

def juntar_ids(): # funcao para reduzirmos as 1180 para as 771 que queremos
    files = [f for f in listdir(PATH_MUSICAS_TODAS) if isfile(join(PATH_MUSICAS_TODAS, f))]

    dataset_id_quadrant = pd.read_csv(PATH_IDS,sep=',', encoding='utf-8',header=None)
    data_id = dataset_id_quadrant.iloc[:, 0]
    data_quadrant = dataset_id_quadrant.iloc[:, 1]

    contador = 0


    for ficheiro in files:
        nome = ficheiro.split(".txt")[0]
        for elemento in data_id:
            if nome == elemento:
                contador+=1
                copyfile(PATH_MUSICAS_TODAS + "\\" + elemento + ".txt",PATH_MUSICAS_FINAIS+ "\\" + elemento + ".txt")
                break
    

def get_names_for_excel():
    files = [f for f in listdir(PATH_FINAL_SCRIPT_CHORUS) if isfile(join(PATH_FINAL_SCRIPT_CHORUS, f))]

    for f in files:
        print(f.split("script_chorus_")[1].replace(".txt",""))


def print_block(lista):
    for a in lista:
        print(a)

def create_letra(lista):
    lista_aux = list()
    i = 0
    while i < len(lista):
        bloco_auxiliar = list()
        while i < len(lista) and lista[i] != '':
            bloco_auxiliar.append(lista[i])
            i+=1
        if (len(bloco_auxiliar)) != 1:
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
    #get_indices()
    #get_chorus_of_song()
    compare_index_files()