lista_script_chorus = [0,1,3,4,6] # detetou
lista_chorus_apenas = [2,5] # real

nr_refroes_detetados = len(lista_script_chorus)

dif_1 = list(set(lista_script_chorus) - set(lista_chorus_apenas))
dif_2 = list(set(lista_chorus_apenas) - set(lista_script_chorus))

detetado_a_menos = len(dif_2) # chorus menos (resultados[i][3])
detetado_a_mais = len(dif_1) # chorus mais (resultados[i][2])

certos = nr_refroes_detetados - detetado_a_mais

print(detetado_a_menos, detetado_a_mais, certos)