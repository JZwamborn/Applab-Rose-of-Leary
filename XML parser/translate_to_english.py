import pandas as pd
from googletrans import Translator
from json.decoder import JSONDecodeError

'''
In this script the output file of the xmlparser is further pre-processed. The speaker's texts are 
translated to English. After that the newly translated sentences (+ the other information available) are
stored into an xlsx file, which can be used by the classifier'''

dataframe = pd.read_csv('output.csv', names=('speaker', 'id', 'text', 'class', 'x', 'y'))
all_texts = dataframe.text

translator = Translator()
english_texts = []


for text in all_texts:
    try:
        translation = translator.translate(text).text
    except JSONDecodeError as err:
        english_texts.append(' ')
        print(err)  # if you want to see when error happens
    else:
        english_texts.append(translation)



dataframe['text'] = english_texts

writer = pd.ExcelWriter('translated_texts.xlsx')
dataframe.to_excel(writer,'Sheet1')
writer.save()


