#!/usr/bin/env python3
# -*- coding: utf-8 -*-
"""
Created on Wed Nov 21 14:08:43 2018

@author: miaaltieri
"""

"""
Created on Wed Jul 12 22:34:23 2017

@author: James
"""


import urllib
from bs4 import BeautifulSoup

import pickle
#from pattern.en import tag
#from pattern.en import parse
import re
import csv
import os
import sys
import nltk, string
from sklearn.feature_extraction.text import TfidfVectorizer
#import pandas as pd
#import numpy as np
#import scipy.stats as stats

import numpy as np
from sklearn import linear_model
#from sklearn.metrics import r2_score, mean_squared_error, mean_absolute_error
#from sklearn.feature_selection import SelectKBest, f_regression
#from sklearn.cross_validation import cross_val_score, KFold
from sklearn.preprocessing import normalize
#import csv
#This tries to be similar to LIWC, even LIWC's more questionable decisions....
#The primary interface is score_text(),
#  though you may want to use Dictionary's score_word() directly on occasion.
#This was built for LIWC's 2007 dictionary with category names/numbers in the dic file
#A line with "%" should precede the category list, if not, expect crashing
#Only words starting with alphanumeric characters count towards the "Word Count"
#  and are used in the normalizing denominator.
#Parenthesis are counted individually, not in groups of two
#Please be careful when editing the code, it is more complicated than would be ideal

# Known discrepancy with LIWC 2007 dict:
# A few words have categories listed multiple times in the 2007 dic, LIWC counts them multiple times. This script does not.
# E.g. text:
# normal
# wc: 100 sw 200 Cogmech
# 
# Really LIWC? 200% of words are cognitive mechanisms?
#
# "like" is silly.
#
# Here's the entry in 2007:
# like    (02 134)125/464 (02 134)126 (02 134)126 253
#
# This means if it follows a pronoun or dicrep (hope, could, etc.), then it should be affect and posemo but posemo is listed twice. If it doesn't follow those it should be a filler. And to top it off, category 253 applies either way. 253 is "Time". I'm guessing there's some data entry error here.
#
# The words "normal" and "wrote" and stems "coroner*", "societ*", "stopper*" have similar issues.
#

import re
import string
from collections import Counter, defaultdict
from string import punctuation

maxInt = sys.maxsize
decrement = True

while decrement:
    # decrease the maxInt value by factor 10 
    # as long as the OverflowError occurs.

    decrement = False
    try:
        csv.field_size_limit(2147483647)
    except OverflowError:
        maxInt = int(maxInt/10)
        decrement = True

'''
#Load the emotion lexicon dictionary
p_file = open("Emotion-Lexicon-Dictionary.p","rb")
emo_dic = pickle.load(p_file)
p_file.close()

#Load the subjective lexicon dictionary
p_file = open("subjective_lexicon_dic.p","rb")
subj_dic = pickle.load(p_file)
p_file.close()'''

all_feats = [
    'Unique Words',
    'Dictionary Words',
    'Other Punctuation',
    'Numerals',
    'Six Letter Words',
    'Word Count',
    'Sentences',
    'Words Per Sentence',
    'Total Function Words',
    'Total Pronouns',
    'Personal Pronouns',
    'First Person Singular', 
    'First Person Plural', 
    'Second Person',
    'Third Person Singular', 
    'Third Person Plural', 
    'Impersonal Pronouns', 
    'Articles',
    'Common Verbs', 
    'Auxiliary Verbs', 
    'Past Tense', 
    'Present Tense', 
    'Future Tense', 
    'Adverbs', 
    'Prepositions', 
    'Conjunctions', 
    'Negations', 
    'Quantifiers', 
    'Number', 
    'Swear Words', 
    'Social Processes', 
    'Family', 
    'Friends', 
    'Humans', 
    'Affective Processes',
    'Positive Emotion', 
    'Negative Emotion', 
    'Anxiety', 
    'Anger', 
    'Sadness', 
    'Cognitive Processes', 
    'Insight',
    'Causation', 
    'Discrepancy', 
    'Tentative', 
    'Certainty', 
    'Inhibition', 
    'Inclusive', 
    'Exclusive', 
    'Perceptual Processes', 
    'See', 
    'Hear', 
    'Feel', 
    'Biological Processes', 
    'Body', 
    'Health', 
    'Sexual',
    'Ingestion',
    'Relativity', 
    'Motion', 
    'Space', 
    'Time', 
    'Work',
    'Achievement', 
    'Leisure', 
    'Home', 
    'Money', 
    'Religion', 
    'Death', 
    'Assent', 
    'Nonfluencies', 
    'Fillers', 
    'Total first person',
    'Total third person',
    "weak-negative",
    "strong-negative",
    "weak-positive",
    "strong-positive",
    'strong_pos_adj',
    'acknowledge',
    'cause_verbs',
    'you_mod',
    'if-you',
    'please_verb',
    'negative_jargon',
    'smiley',
    'greetings',
    'congrats',
    'welcome']

_liwc_categories = [
    'Unique Words',
    'Dictionary Words',
    'Other Punctuation',
    'Numerals',
    'Six Letter Words',
    'Word Count',
    'Sentences',
    'Words Per Sentence',
    'Total Function Words',
    'Total Pronouns',
    'Personal Pronouns',
    'First Person Singular', 
    'First Person Plural', 
    'Second Person',
    'Third Person Singular', 
    'Third Person Plural', 
    'Impersonal Pronouns', 
    'Articles',
    'Common Verbs', 
    'Auxiliary Verbs', 
    'Past Tense', 
    'Present Tense', 
    'Future Tense', 
    'Adverbs', 
    'Prepositions', 
    'Conjunctions', 
    'Negations', 
    'Quantifiers', 
    'Number', 
    'Swear Words', 
    'Social Processes', 
    'Family', 
    'Friends', 
    'Humans', 
    'Affective Processes',
    'Positive Emotion', 
    'Negative Emotion', 
    'Anxiety', 
    'Anger', 
    'Sadness', 
    'Cognitive Processes', 
    'Insight',
    'Causation', 
    'Discrepancy', 
    'Tentative', 
    'Certainty', 
    'Inhibition', 
    'Inclusive', 
    'Exclusive', 
    'Perceptual Processes', 
    'See', 
    'Hear', 
    'Feel', 
    'Biological Processes', 
    'Body', 
    'Health', 
    'Sexual',
    'Ingestion',
    'Relativity', 
    'Motion', 
    'Space', 
    'Time', 
    'Work',
    'Achievement', 
    'Leisure', 
    'Home', 
    'Money', 
    'Religion', 
    'Death', 
    'Assent', 
    'Nonfluencies', 
    'Fillers', 
    'Total first person',
    'Total third person']

#LIWC features



_dictionary = None
def load_dictionary(filename):
    global _dictionary
    _dictionary = Dictionary(filename)

def default_dictionary_filename():
    return os.path.abspath(os.path.join(os.path.dirname(__file__), 'LIWC2007_English080730.dic'))

# might be better to split on whatever... but this seems about right
_liwc_tokenizer = re.compile(r'(\d[^a-z\(\)]*|[a-z](?:[\'\.]?[a-z])*|(?<=[a-z])[^a-z0-9\s\(\)]+|[\(\)][^a-z]*)',re.UNICODE|re.IGNORECASE)

#tests:
#jan 1, 2010 = 2 words
#1 jan, 2010 = 3 words
#Jan 1? so soon! = 1 sentence
#lkhj.iou = 1 word
#lkhj.'iou = 2 words
#lkhj'.iou = 1 word
# 1)2)3)4)5)6)7)8)9 = 2 words...?
# 1]2]3]4]5}6}7}8]9 = 1 word
# 1 2 3 4 5 6 7 8 9 = 1 word
# 1!2@3#4$5%6^7&8*9~0`0'0;0:0[0{0]0}0.0,0-0_0=0+0|0\0>0<0"0 0 0.0 = 1 word
# 1..2 = 1 word
# a..b = 2 words
# a.b = 1 word
# the "cat". the dog. = 1 sentence
# b :) :) c :) = 4 words, two unique
# asdf... = not a sentence
# asdf = not a sentence
# Mr. = sentence



def find_smilies(text):
    smilies = [":)", ":P", ":b", ":-)", 
               ":-P", ":-b", ";)", ";P", 
               ";b", ";-)", ";-P", ";-b", 
               "^_^", "=)", "=]"]

    eyes, noses, mouths = r":;8BX=^", r"-~'^_", r")(/\|DP^]"
    pattern1 = "[%s][%s]?[%s]" % tuple(map(re.escape, [eyes, noses, mouths]))
    counts = Counter(re.findall(pattern1,text))
    temp_counts = {}
    for smile in counts:
        if smile in smilies:
            temp_counts[smile] = counts[smile]
    return sum([temp_counts[item] for item in temp_counts])


#Parts of speech labels
pos_tags = ["CC","CD","DT","EX","FW","IN","JJ","JJR","JJS","LS","MD","NN","NNS","NNP","NNPS","PDT","POS","PRP","PRP$","RB","RBR","RBS","RP","SYM","TO","UH","VB","VBD","VBG","VBN","VBP","VBZ","WDT","WP","WP$","WRB"]
#Emotion lexicon labels
emo_cats = ['anticipation_emo', 'joy_emo', 'negative_emo', 'sadness_emo', 'disgust_emo', 'positive_emo', 'anger_emo', 'surprise_emo', 'fear_emo', 'trust_emo']
 
subj_cats = ["weak-negative","strong-negative","weak-positive","strong-positive"]

#subj_dic = pickle.load(open('subjective_lexicon_dic.p','rb'))
POS = {"JJ":"adj",
       "JJR":"adj",
       "JJS":"adj",
       "NN":"noun",
       "NNS":"noun",
       "NNP":"noun",
       "NNPS":"noun",
       "PRP":"noun",
       "PRP$":"noun",
       "RB":"adverb",
       "RBR":"adverb",
       "RB$":"adverb",
       "VB":"verb",
       "VBD":"verb",
       "VBG":"verb",
       "VBN":"verb",
       "VBP":"verb",
       "VBZ":"verb"
        }

punct_feats = {
        "Period" : ".",
        "Comma": ",",
        "Colon": ":",
        "Semi Colon": ";",
        "Question Mark": "?",
        "Exclamation Mark": "!",
        "Dash": "-",
        "Quote": '"',
        "Apostrophe": "'",
        "Parenthesis": "()",
        "Other Punctuation": '#$%&*+/<=>@[\\]^_`{|}~',
        "All Punctuation": ""
        }

strong_pos_adjectives = ["incredible", "wellwritten", "great", "excellent",
                         "successful", "outstanding", "impressive", "best", "highest",
                         "outstanding", "featured", "greatest", "awesome",
                         "fantastic", "nice", "beautiful", "good"]   

cause_verbs = ["make", "have", "get", "let", "help", "collaborate", "advise",
               "advocate", "anticipate", "ask", "command", "expect",
               "desire", "demand", "insist", "hope", "necessitate", "need",
               "postulate","propose", "recommend", "request", "require",
               "suggest", "urge", "want", "wish"]  

you_mod = ["you can", "you could", "you shall", "you may", "you might", 
           "you must", "you should", "you will", "you would",
           "you'd", "can you", "could you", "shall you", "may you", 
           "might you", "must you", "should you", "will you", "would you"]

negative_jargon = ["spam","revert","reverted", "block", "blocked",
                   "vandalism","vandalise","vandalised", "vandalized",
                   "vandalize", "violate", "violated", "nonfree", "copyright",
                   "remove", "disputed", "dispute", "noneutral", "fair use"] 

smile = ["smile", "smiles", "smiling", "balloon", "balloons"]  

greetings = ["hi", "hey", "ha", "hello" , "cheers", 
             "regards", "new year", "merry christmas", 
             "see you", "good day", "nice day"]

congrats = ["congrats", "congratulation", "congratulations"]

acknowledge = ["thank you for","thanks for"]

def get_feats(text):
    feats = {}
    for elem in subj_cats:
        feats[elem] = 0.0   
    feats["strong_pos_adj"] = 0.0
    feats["acknowledge"] = 0.0    
    feats["cause_verbs"] = 0.0
    feats["you_mod"] = 0.0
    feats["please_verb"] = 0.0
    feats["if-you"] = 0.0
    feats["negative_jargon"] = 0.0
    feats["smiley"] = 0.0
    feats["greetings"] = 0.0
    feats["congrats"] = 0.0
    feats["welcome"] = 0.0    
    for feat in _liwc_categories:
        feats[feat] = 0.0
    for feat in pos_tags:
        feats[feat] = 0.0
    for feat in emo_cats:
        feats[feat] = 0.0
    
		#Use pattern.tag to get POS tags
    tags = tag(text)
		#Gather only the tags from the output of pattern.tag
    ptags = [t for w,t in tags]
		#Dictionary used for hold the counts for each POS tag
    tag_dic = {}
		#Create Dictionary element for each possible tag
    for t in pos_tags:
        tag_dic[t] = 0.0
		#Count number of tags found within the text sample
    for t in ptags:
        if t in tag_dic:
            tag_dic[t] += 1
		#Append each tag frequency to the row
		#If tag was not present append 0
    for t in pos_tags:
        if len(ptags) == 0:
            feats[t] = 0.0
        else:
            feats[t] = (tag_dic[t] / float(len(ptags)))
    #subj_parsed = parse(text,chunks=False,lemmata=True).split(' ') 
    	#EMOTION LEXICON FEATURE EXTRACTION
		#Use pattern.parse to get the parsed text
    words = []
    results = {}
		#Dictionary used for hold the counts for each emotional category
    text_cats = {}
		#Initialize all categories to 0 count
    for cat in emo_cats:
        text_cats[cat] = 0.0
    for key in emo_dic[emo_dic.keys()[0]]:
        results[key] = 0.0
		#Separate the words given from pattern.parse into a list
    #    for word_emo in subj_parsed:
    #        words.append(word_emo.split('/')[0])
		
    for word in words:
			#To ensure the word is in the lexicon match the case by making all text lower case
        word = word.lower()
			#Count the number of times a category is found
        if emo_dic.has_key(word):
            for cat in emo_dic[word]:
                text_cats[cat] += float(emo_dic[word][cat])
		#Append the emotion category frequency to the row
    for cat in text_cats:
        text_cats[cat] = text_cats[cat]/len(words)
        feats[cat] = text_cats[cat] 
    #Get Strong positive adjectives feature
    for word_strong in strong_pos_adjectives:
        feats["strong_pos_adj"] += sum(1 for _ in re.finditer(r'\b%s\b' % re.escape(word_strong), text.lower()))
    #Get Causative/subjunctive verbs
    for word_cause in cause_verbs:
        feats["cause_verbs"] += sum(1 for _ in re.finditer(r'\b%s\b' % re.escape(word_cause), text.lower()))
    #Get YouMod features
    for word_you in you_mod:
        feats["you_mod"] += sum(1 for _ in re.finditer(r'\b%s\b' % re.escape(word_you), text.lower()))
    #Get Negative Jargon features
    for word_neg in negative_jargon:
        feats["negative_jargon"] += sum(1 for _ in re.finditer(r'\b%s\b' % re.escape(word_neg), text.lower()))
    #Get Smiley features
    for word_smile in smile:
        feats["smiley"] += sum(1 for _ in re.finditer(r'\b%s\b' % re.escape(word_smile), text.lower()))
    feats["smiley"] += find_smilies(text)
    #Get Greetings features
    for word_greet in greetings:
        feats["greetings"] += sum(1 for _ in re.finditer(r'\b%s\b' % re.escape(word_greet), text.lower()))
    #Get congrats features
    for word_congrats in congrats:
        feats["congrats"] += sum(1 for _ in re.finditer(r'\b%s\b' % re.escape(word_congrats), text.lower()))
    #Get welcome features
    feats["welcome"] += sum(1 for _ in re.finditer(r'\b%s\b' % re.escape("welcome"), text.lower()))
    #Get Acknowledge features
    for word_ack in acknowledge:
        feats["acknowledge"] += sum(1 for _ in re.finditer(r'\b%s\b' % re.escape(word_ack), text.lower()))
    #Get if-you features
    feats["if-you"] += sum(1 for _ in re.finditer(r'\b%s\b' % re.escape("if you"), text.lower()))
    
    for index in range(len(subj_parsed)):
        word = subj_parsed[index]
        if word != '':
            
            parts = word.split("/")
            w = parts[0]
            pos = parts[1]
            lem = parts[2]
            #Get Subjectivity Features 
            if w.lower() in subj_dic:
                pos_temp = POS.get(pos,"anypos")
                if pos_temp in subj_dic[w.lower()]:
                    subj = subj_dic[w.lower()][pos_temp]["type"].replace("subj","")
                    polarity = subj + "-" + subj_dic[w.lower()][pos_temp]['priorpolarity']
                    if polarity in subj_cats:
                        feats[polarity] += 1
            elif lem in subj_dic:
                pos_temp = POS.get(pos,"anypos")
                if pos_temp in subj_dic[lem]:
                    subj = subj_dic[lem][pos_temp]["type"].replace("subj","")
                    polarity = subj + "-" + subj_dic[lem][pos_temp]['priorpolarity']
                    if polarity in subj_cats:
                        feats[polarity] += 1
            #Get Please features
            if w.lower() == "please":
                if index != len(subj_parsed)-1:
                    next_word = subj_parsed[index+1]
                    i = 2
                    flag = True
                    while(next_word == '' and flag):
                        if index+i >= len(subj_parsed):
                            flag = False
                            break
                        next_word = subj_parsed[index+i]
                        i += 1
                    if flag:
                        next_parts = next_word.split("/")
                        next_pos = pos_temp = POS.get(next_parts[1],"anypos")
                        if next_pos == "verb":
                            feats["please_verb"] += 1
    
    liwc_feats = score_text(text)
    for feat in liwc_feats:
        if feat in feats:
            feats[feat] = liwc_feats[feat]
    for feat in feats:
        if feat not in liwc_feats:
#            print(feat)
#            print(type(feats[feat]))
#            print(liwc_feats['Word Count'])
##            print(type(liwc_feats['Word Count']))
#            if feat == "subj":
#                for elem in subj_cats:
#                    feats[feat][elem] = (feats[feat][elem] / liwc_feats['Word Count'])*100.0
#            else:
            if liwc_feats['Word Count'] == 0:
                feats[feat] = 0.0
            else:
                feats[feat] = (feats[feat] / liwc_feats['Word Count'])*100.0
    for feat in punct_feats:
        count = 0
        for char in punct_feats[feat]:
            count += text.count(char)
        feats[feat] = count
    return feats

if __name__ == '__main__': 
    load_dictionary('LIWC2007_English080730.dic')
    filepath = r'C:\Users\Red\Desktop\Investigacao2020\datasets\dataset_771+180 (400+110)\\771\mt0000022649.txt'
    with open(filepath,"r") as f:
        linhas = f.read().strip()
    
    print(get_feats(linhas))

feat_list_model_order = [
        "Word Count",
        "Words Per Sentence",
        "Six Letter Words",
        "Dictionary Words",
        "Numerals",
        "Total Function Words",
        "Total Pronouns",
        "Personal Pronouns",
        "First Person Singular",
        "First Person Plural",
        "Second Person",
        "Third Person Singular",
        "Third Person Plural",
        "Impresonal Pronouns",
        "Articles",
        "Common Verbs",
        "Auxiliary Verbs",
        "Past Tense",
        "Present Tense",
        "Future Tense",
        "Adverbs",
        "Propositions",
        "Conjuctions",
        "Negations",
        "Quantifiers",
        "Number",
        "Swear Words",
        "Social Processes",
        "Family",
        "Friends",
        "Humans",
        "Affective Processes",
        'Positive Emotion', 
        'Negative Emotion', 
        'Anxiety', 
        'Anger', 
        'Sadness', 
        'Cognitive Processes', 
        'Insight',
        'Causation', 
        'Discrepancy', 
        'Tentative', 
        'Certainty', 
        'Inhibition', 
        'Inclusive', 
        'Exclusive', 
        'Perceptual Processes', 
        'See', 
        'Hear', 
        'Feel', 
        'Biological Processes', 
        'Body', 
        'Health', 
        'Sexual',
        'Ingestion',
        'Relativity', 
        'Motion', 
        'Space', 
        'Time', 
        'Work',
        'Achievement', 
        'Leisure', 
        'Home', 
        'Money', 
        'Religion', 
        'Death', 
        'Assent', 
        'Nonfluencies', 
        'Fillers', 
        "Period",
        "Comma",
        "Colon",
        "Semi Colon",
        "Question Mark",
        "Exclamation Mark",
        "Dash",
        "Quote",
        "Apostrophe",
        "Parenthesis",
        "Other Punctuation",
        "All Punctuation",
        "CC",
        "CD",
        "DT",
        "EX",
        "FW",
        "IN",
        "JJ",
        "JJR",
        "JJS",
        "LS",
        "MD",
        "NN",
        "NNS",
        "NNP",
        "NNPS",
        "PDT",
        "POS",
        "PRP",
        "PRP$",
        "RB",
        "RBR",
        "RBS",
        "RP",
        "SYM",
        "TO",
        "UH",
        "VB",
        "VBD",
        "VBG",
        "VBN",
        "VBP",
        "VBZ",
        "WDT",
        "WP",
        "WP$",
        "WRB",
        "anticipation_emo",
        "joy_emo",
        "negative_emo",
        "sadness_emo",
        "disgust_emo",
        "positive_emo",
        "anger_emo",
        "surprise_emo",
        "fear_emo",
        "trust_emo",
        "Weak-Subjective",
        "Strong-Subjective"
        ]