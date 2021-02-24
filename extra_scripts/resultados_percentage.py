# -*- coding: utf-8 -*-
import pandas as pd


def resultados_perfect_match():
    data_total = pd.read_csv("D:/mer/FeatureExtractionSystem/datasets/resultados_refrao.csv",sep=';', encoding='utf-8')
    dataset_total_nome = data_total.iloc[:, 0]
    dataset_total_chorus_certo = data_total.iloc[:, 1]
    dataset_total_chorus_errado = data_total.iloc[:, 2]
    dataset_total_chorus_mais = data_total.iloc[:, 3]
    dataset_total_chorus_menos = data_total.iloc[:, 4]
    dataset_total_chorus_oficial = data_total.iloc[:, 5]
    print(data_total)

    contador=0
    contador_errado=0
    contador_a_mais=0
    contador_a_menos=0
    contador_a_mais_menos=0

    for i in range(len(data_total)):
        
        if(dataset_total_chorus_certo[i]==dataset_total_chorus_oficial[i]):
            if(dataset_total_chorus_mais[i]==0 and dataset_total_chorus_menos[i]==0):
                contador+=1
               
            if(dataset_total_chorus_mais[i]>0 and dataset_total_chorus_menos[i]==0):
                contador_a_mais+=1
               
        if (dataset_total_chorus_certo[i]==0):
            if(dataset_total_chorus_mais[i]==0 and dataset_total_chorus_menos[i]==0):
                contador_errado+=1
             
        if(dataset_total_chorus_certo[i] < dataset_total_chorus_oficial[i]):
            if(dataset_total_chorus_menos[i]>0 and dataset_total_chorus_mais[i]==0):
                contador_a_menos+=1
               
        if(dataset_total_chorus_menos[i]>0 and dataset_total_chorus_mais[i]>0):
            contador_a_mais_menos+=1
            

    print(contador)
    print(contador/len(data_total)*100)
    print(contador_errado)
    print(contador_errado/len(data_total)*100)
    print(contador_a_mais)
    print(contador_a_mais/len(data_total)*100)
    print(contador_a_menos)
    print(contador_a_menos/len(data_total)*100)
    print(contador_a_mais_menos)
    print(contador_a_mais_menos/len(data_total)*100)










if __name__ == '__main__':
    resultados_perfect_match()