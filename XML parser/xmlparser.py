import xml.etree.ElementTree as ET
import pandas as pd

tree = ET.parse('dataset.xml')
yomama = tree.findall(".//text")
yomama = tree.findall(".//turn")
matrix = []
for turn in yomama:
    for sentence in turn.findall(".//sentence"):
        record = []
        record.append(turn.findall(".//speaker")[0].text)
        record.append(sentence.findall(".//id")[0].text)
        record.append(sentence.findall(".//text")[0].text)
        record.append(sentence.findall(".//label")[0].text)
        record.append(sentence.findall(".//xpos")[0].text)
        record.append(sentence.findall(".//ypos")[0].text)
        matrix.append(record)


my_df = pd.DataFrame(matrix)
my_df.to_csv('output.csv', index=False, header=False)

print(yomama[0].text)

