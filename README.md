# Feature-Extraction-System

Project done for CISUC by Hugo Redinho and Carolina Gonçalves.

Article:
Malheiro, R., Panda, R., Gomes, P., & Paiva, R. P. (2018). Emotionally-Relevant Features for Classification and Regression of Music Lyrics. IEEE Transactions on Affective Computing – TAFFC, 9(2), 240–254. [DOI Link](https://doi.org/10.1109/TAFFC.2016.2598569)

Consists of a feature extractor for song lyrics consisting of:
- Semantic Features
- Structural Features
- Content-Based Features
- Structural-Based Features

Every one of them has sub-features that can be extracted, either all at once or one at a time.

The project can be run in two ways:
By running the main class [MainInterface.java](src/Interface/MainInterface.java) and then a interactive menu will pop up:

![alt text](main_window.png)

In this window you can choose the type of feature selection you want, and then a sub-menu will pop up, or you can choose to select a folder to extract the files from.

If none is selected, the default folder is [Origem](src/Origem).
One example of the sub-menu (for Semantic Features) is:
![alt text](semantic_features.png)

Another way the project can be running is by using the [MainInterface.jar](MainInterface.jar) and running the following command:

*java -jar MainInterface.jar typeOfExtraction input outputFile* 

In terms of input (it can either be a file or a folder containing only the files with the right structure, the program will determine which one it is).
*Note:* Not all feature extraction types will extract to a csv, please look at the list below to see which ones do. 


The type of extraction can be one of the following:
- features_gi (for GI features)
    - Extracts to csv
- features_synesktech (for Synesktech features)
    - Extracts to csv
- features_dal_anew (for DAL & ANEW features)
    - Extracts to csv & also outputs a .txt with details
- features_gazeteers (for Gazeteers features)
    - Extracts to csv
- features_slang (for Slang features)
    - Extracts to csv
- features_capitalltetters (for Capital Letters features)
    - Extracts to csv
- features_standardPOS (for standard POS tagger features)
    - For each input file outputs a .txt with details
- features_cbf* (for content based features)
    - Extracts to csv
- features_titulo* (to extract the number of ocorrences of the title in a song)
    - Extracts to csv
- semantic_features (Extracts all semantic features onto a compiled csv file)
- stylistic_features (Similar to above, but for stylistic features)
- all_features (Combines the above extractions, also extracting semantic to "Semantic.csv" and stylistic to "Stylistic.csv")


One example would be: *java -jar MainInterface.jar "features_gi" "src/Origem" "outputFile.csv"*

Note: The features assigned with * have a different command line structure.

For *features_CBF* since it has more arguments, it has to be run in a different manner:

*java -jar MainInterface.jar features_cbf inputDirectory arg1 arg2 arg3*

Note: There's no outputFile path you can specify, the program makes it based on the arguments, check file [SaveInstancesInFile.java](src/AuxiliarFiles/SaveInstancesInFile.java) for details.

In regards to arg1, arg2 and arg3, they are parameters for the program, and they can be:
- *arg1*: unig, big, trig, 4grams, 5grams
- *arg2*: nada, st, sw, st+sw
- *arg3*: freq, bool, tfidf, norm

So one example would be: *java -jar MainInterface.jar "features_cbf" "src/Origem/" "unig" "nada" "freq"*

The name of the outputFile created is the combination of all three arguments, so for the example above the output file would be "unig_nada_freq.csv".

For *features_titulo* since you have to input the title of the file, the structure is as such:

*java -jar MainInterface.jar features_titulo inputFile outputFile titleSong"

So an example: *java -jar MainInterface.jar "features_titulo" "src/Lirica/tw_bl.txt" "tw_bl.txt" "Blinding Lights"*
