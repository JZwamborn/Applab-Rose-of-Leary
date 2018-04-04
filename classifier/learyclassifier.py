from nltk.corpus import stopwords
from nltk.tokenize import wordpunct_tokenize, sent_tokenize
import nltk
import sklearn.datasets
import sklearn.metrics
import sklearn.model_selection
import numpy as np
from sklearn.svm import SVC
from nltk.stem.snowball import SnowballStemmer
from nltk.probability import FreqDist
from nltk.sentiment.vader import SentimentIntensityAnalyzer
import re
import copy
from sumy.nlp.tokenizers import Tokenizer
from sumy.parsers.plaintext import PlaintextParser
from sumy.summarizers.lex_rank import LexRankSummarizer
import csv
import pandas as pd

# Download the 'stopwords' and 'punkt' from the Natural Language Toolkit, you can comment the next lines if already present.
# nltk.download('stopwords')
# nltk.download('punkt')
# nltk.download('averaged_perceptron_tagger')
nltk.download('vader_lexicon')
stop_words = set(stopwords.words('english'))
stemmer = SnowballStemmer("english")


# Load the dataset into memory from the filesystem
def load_data(dir_name):
    data = pd.read_excel("learysetn.xlsx", header=None)
    data = data.as_matrix()
    data = np.delete(data, 0, 0)
    return data


def load_train_data():
    return load_data('train')


def load_test_data():
    return load_data('test')


def flatten(list_of_lists):
    flattened_list = []
    for x in list_of_lists:
        for y in x:
            flattened_list.append(y)
    return flattened_list


# Extract features from a given text
def extract_features(text):
    bag_of_words = [x for x in wordpunct_tokenize(text)]
    bag_of_tagged_words = nltk.pos_tag(bag_of_words)
    stemmed_bag_of_words = [stemmer.stem(x) for x in bag_of_words]
    bag_of_chars = flatten([list(x) for x in bag_of_words])
    sentence_tokenizer = nltk.data.load('tokenizers/punkt/english.pickle')
    word_tokenizer = nltk.tokenize.RegexpTokenizer(r'\w+')
    bag_of_words = [x for x in wordpunct_tokenize(text)]
    tokens = nltk.word_tokenize(text.lower())
    words = word_tokenizer.tokenize(text.lower())
    sentences = sentence_tokenizer.tokenize(text)
    vocab = set(words)
    vowels = ['a', 'e', 'o', 'i', 'u']
    words_per_sentence = np.array([len(word_tokenizer.tokenize(s))
                                   for s in sentences])

    # tokenize review to sentences
    sent = sent_tokenize(text)

    # get the first and last two sentences per review
    # if len(sent) >= 4:
    #     first_and_last_sent = ' '.join([sent[0], sent[1], sent[-2], sent[-1]])
    # else:
    #     first_and_last_sent = ' '.join(sent)
    # print('First 2 / Last 2:')
    # print(first_and_last_sent)
    # print()

    # # Automatic summarization
    # summarizer = LexRankSummarizer()
    # parser = PlaintextParser.from_string(text, Tokenizer("english"))
    # summary = summarizer(parser.document, 6)
    # summary = list(summary)
    # summary_str = ''
    # for s in summary:
    #     summary_str = summary_str + str(s) + ' '
    #
    # print('Automatic Summary')
    # print(summary_str)
    # print()

    sid = SentimentIntensityAnalyzer()


    features = []
    # Example feature 1: count the number of words
    features.append(len(bag_of_words))

    # Example feature 2: count the number of words, excluded the stopwords
    features.append(len([x for x in bag_of_words if x.lower() not in stop_words]))

    # feature 3: total number of chars
    features.append(len(bag_of_chars))

    # feature 4: total number of periods
    features.append(len([x for x in bag_of_chars if x == '.']))

    # feature 5: total number of commas
    features.append(len([x for x in bag_of_chars if x == ',']))

    # feature 6: total number of exclamation marks
    features.append(len([x for x in bag_of_chars if x == '!']))

    # feature 7: total number of colons
    features.append(len([x for x in bag_of_chars if x == ':']))

    # feature 8: total number of semi-colons
    features.append(len([x for x in bag_of_chars if x == ';']))

    # feature 9: total number of brackets
    features.append(len([x for x in bag_of_chars if x == '(' or x == ')']))

    # feature 10: total number of quotation marks
    features.append(len([x for x in bag_of_chars if x == '"']))

    # feature 11: total number of question marks
    features.append(len([x for x in bag_of_chars if x == '?']))

    # feature 12: total number of a's
    features.append(len([x for x in bag_of_chars if x.lower == 'a']))

    # feature 13: total number of b's
    features.append(len([x for x in bag_of_chars if x.lower == 'b']))

    # feature 14: total number of c's
    features.append(len([x for x in bag_of_chars if x.lower == 'c']))

    # feature 14: total number of d's
    features.append(len([x for x in bag_of_chars if x.lower == 'd']))

    # feature 15: total number of e's
    features.append(len([x for x in bag_of_chars if x.lower == 'e']))

    # feature 16: total number of f's
    features.append(len([x for x in bag_of_chars if x.lower == 'f']))

    # feature 17: total number of g's
    features.append(len([x for x in bag_of_chars if x.lower == 'g']))

    # feature 18: total number of h's
    features.append(len([x for x in bag_of_chars if x.lower == 'h']))

    # feature 19: total number of i's
    features.append(len([x for x in bag_of_chars if x.lower == 'i']))

    # feature 20: total number of j's
    features.append(len([x for x in bag_of_chars if x.lower == 'j']))

    # feature 21: total number of k's
    features.append(len([x for x in bag_of_chars if x.lower == 'k']))

    # feature 22: total number of l's
    features.append(len([x for x in bag_of_chars if x.lower == 'l']))

    # feature 23: total number of m's
    features.append(len([x for x in bag_of_chars if x.lower == 'm']))

    # feature 24: total number of n's
    features.append(len([x for x in bag_of_chars if x.lower == 'n']))

    # feature 25: total number of o's
    features.append(len([x for x in bag_of_chars if x.lower == 'o']))

    # feature 26: total number of p's
    features.append(len([x for x in bag_of_chars if x.lower == 'p']))

    # feature 27: total number of q's
    features.append(len([x for x in bag_of_chars if x.lower == 'q']))

    # feature 28: total number of r's
    features.append(len([x for x in bag_of_chars if x.lower == 'r']))

    # feature 29: total number of s's
    features.append(len([x for x in bag_of_chars if x.lower == 's']))

    # feature 30: total number of t's
    features.append(len([x for x in bag_of_chars if x.lower == 't']))

    # feature 31: total number of u's
    features.append(len([x for x in bag_of_chars if x.lower == 'u']))

    # feature 32: total number of v's
    features.append(len([x for x in bag_of_chars if x.lower == 'v']))

    # feature 33: total number of w's
    features.append(len([x for x in bag_of_chars if x.lower == 'w']))

    # feature 34: total number of x's
    features.append(len([x for x in bag_of_chars if x.lower == 'x']))

    # feature 35: total number of y's
    features.append(len([x for x in bag_of_chars if x.lower == 'y']))

    # feature 36: total number of z's
    features.append(len([x for x in bag_of_chars if x.lower == 'z']))

    # feature 37: total number of numbers
    features.append(len([x for x in bag_of_chars if x.lower in ['0', '1', '2', '3', '4', '5', '6', '7', '8', '9']]))

    # feature 38: total number of capitals
    features.append(len([x for x in bag_of_chars if x.isupper()]))

    # feature 39: average word length
    features.append(np.mean([len(x) for x in bag_of_words]))

    # feature 40: amount of different words
    features.append(len([x for x in set(stemmed_bag_of_words)]))

    # feature 41: average sentence length
    features.append(np.mean([len(wordpunct_tokenize(x)) for x in sent]))

    # feature 42-78: amount of occurences per POS tag
    for t in ['CC', 'CD', 'DT', 'EX', 'FW', 'IN', 'JJ', 'JJR', 'JJS', 'LS', 'MD', 'NN', 'NNS', 'NNP', 'NNPS', 'PDT', 'POS', 'PRP', 'PRP$', 'RB', 'RBR', 'RBS', 'RP', 'SYM', 'TO', 'UH', 'VB', 'VBD', 'VBG', 'VBN', 'VBP', 'VBZ', 'WDT', 'WP', 'WP$', 'WRB']:
        features.append(len([x for x in bag_of_tagged_words if x[1] == t]))

    # feature 79: amount of different words (unstemmed)
    features.append(len([x for x in set(bag_of_words)]))

    # feature 80: amount of hapaxes (unstemmed)
    fdist = FreqDist(word.lower() for word in bag_of_words)
    features.append(len(fdist.hapaxes()))

    # feature 82: total amount of sentences
    features.append(len([x for x in sent]))

    # feature 83: total number of white space characters
    features.append(len([x for x in bag_of_chars if x.lower == ' ']))

    # feature 84: total number of tab characters
    features.append(len([x for x in bag_of_chars if x.lower == '\t']))

    #feature38: function words frequency of and
    features.append(text.count("and"))
    # feature39: function words frequency of an
    features.append(text.count("an"))
    # feature40: function words frequency of between
    features.append(text.count("between"))
    # feature41: function words frequency of in
    features.append(text.count("in"))
    # feature42: function words frequency of about
    features.append(text.count("about"))
    # feature43: function words frequency of nothing
    features.append(text.count("nothing"))
    # feature44: function words frequency of but
    features.append(text.count("but"))
    # feature45: function words frequency of on
    features.append(text.count("on"))
    # feature46: function words frequency of although
    features.append(text.count("although"))
    # feature47: function words frequency of though
    features.append(text.count("though"))
    # feature348: function words frequency of everybody
    features.append(text.count("upon"))
    # feature49: function words frequency of everybody
    features.append(text.count("some"))
    # feature50: function words frequency of everybody
    features.append(text.count("he"))
    # feature51: function words frequency of everybody
    features.append(text.count("she"))
    # feature52: function words frequency of everybody
    features.append(text.count("everybody"))
    # feature53: function words frequency of everybody
    features.append(text.count("us"))
    # feature54: function words frequency of everybody
    features.append(text.count("of"))
    # feature55: function words frequency of everybody
    features.append(text.count("by"))
    # feature56: function words frequency of everybody
    features.append(text.count("off"))
    # feature57: function words frequency of everybody
    features.append(text.count("can"))
    # feature58: function words frequency of everybody
    features.append(text.count("all"))
    # feature59: function words frequency of everybody
    features.append(text.count("is"))

    # feature3: number of just the stopwords
    features.append(len([x for x in bag_of_words if x.lower() in stop_words]))
    # feature4: average number of words per sentence
    features.append(words_per_sentence.mean())
    # feature5: sentence length variation
    features.append(words_per_sentence.std())
    # feature6: lexical diversity
    features.append(len(vocab) / float(len(words)))
    # feature7: number of commas
    features.append(tokens.count(','))
    # feature8: Semicolons
    features.append(tokens.count(';'))
    # feature9: Colons
    features.append(tokens.count(':'))
    # feature10: question marks
    features.append(tokens.count('?'))
    # feature11: explanation marks
    features.append(tokens.count('!'))

    # features 12,13,14,15,16,17: get part of speech for each token in text
    tokens = nltk.word_tokenize(text)
    pos_text = [p[1] for p in nltk.pos_tag(tokens)]

    # count frequencies for common POS types
    pos_list = ['NN', 'NNP', 'DT', 'IN', 'JJ', 'NNS', 'VB', 'POS', 'RB', 'VBD', 'CC',
                'EX', 'WDT', 'WRB', 'WP']
    for pos in pos_list:
        count = pos_text.count(pos)
        # features.append(count)
    # feature18: number of sentences
    features.append(len(sentences))
    # feature19: number of quotations:
    features.append(tokens.count('"'))
    # feature20: average wordlenght
    average = sum(len(word) for word in text) / len(text)
    features.append(average)
    # feature21 std word lenght
    features.append(np.std([len(word) for word in text]))
    # feature22 number of digits
    features.append(len([c for c in text if c.isdigit()]))
    # feature23: number of uppercase chars
    features.append(sum(1 for c in text if c.isupper()))
    # feature24: number of tabs
    features.append(sum(1 for c in text if c == '\t'))
    # feature25: number of brackets
    features.append(tokens.count('('))
    # feature26: number of characters
    features.append(len(text))
    # feature27: number of words without vowel
    vowellist = []
    for word1 in text:
        vowellist.append(sum([1 for i in word1 if i in vowels]))
    vowellist = [x for x in vowellist if x != 0]
    features.append(len(bag_of_words) - len(vowellist))
    # feature28: number of %
    features.append(tokens.count('%'))
    # feature29:number of #
    features.append(tokens.count('#'))
    # feature30:number of enters
    features.append(tokens.count('\n'))
    # feature31:number of uppercase words not at the beginning of the sentence
    upper_list = re.findall(r'(?<!\.\s)\b[A-Z][a-z]*\b', text)
    features.append(len(upper_list))
    # feature32:number of whitespace
    features.append(sum(c.isspace() for c in text))
    # feature33: number of alphabetic chars
    features.append(sum(c.isalpha() for c in text))
    # feature34:number of '
    features.append(tokens.count('\''))
    # feature35:number of -
    features.append(tokens.count('-'))
    # feature 36: 2 letter words
    features.append(len([word for word in text if len(word) == 2]))
    # feature 37: 3 letter words
    features.append(len([word for word in text if len(word) == 3]))
    # feature 37: 1 letter words
    features.append(len([word for word in text if len(word) == 1]))
    # feature38: function words frequency of to
    features.append(text.count("to"))
    # feature35:number of [
    features.append(tokens.count('['))
    # feature 36 multiple punctations:
    features.append(len(re.findall((r'([.,/#!$%^&*;:{}=_`~()-])[.,/#!$%^&*;:{}=_`~()-]+'), text)))
    # feature37: normalized number of the stopwords
    features.append(len([x for x in bag_of_words if x.lower() in stop_words]) / len(text))


    ss = sid.polarity_scores(text)
    features.append(ss.get('pos'))
    features.append(ss.get('neg'))
    features.append(ss.get('neu'))
    features.append(ss.get('compound'))

    return features


# Classify using the features
def classify(train_features, train_labels, test_features):
    # TODO: (Optional) If you would like to test different how classifiers would perform different, you can alter
    # TODO: the classifier here.
    clf = SVC(kernel='linear')
    clf.fit(train_features, train_labels)
    return clf.predict(test_features)


# Evaluate predictions (y_pred) given the ground truth (y_true)
def evaluate(y_true, y_pred, feature=0):
    # TODO: What is being evaluated here and what does it say about the performance? Include or change the evaluation
    # TODO: if necessary.
    recall = sklearn.metrics.recall_score(y_true, y_pred, average='macro')
    precision = sklearn.metrics.precision_score(y_true, y_pred, average='macro')
    f1_score = sklearn.metrics.f1_score(y_true, y_pred, average='macro')
    print("Feature number:\t%d\tRecall:\t%f\t Precision:\t%f\tF1-score:\t%f" % (feature+1, recall, precision, f1_score))
    return recall, precision, f1_score


# The main program
def main():
    train_data = load_train_data()
    print("Data loaded")

    print("Extracting features...")
    # Extract the features
    features = list(map(extract_features, train_data[:, 0]))
    print("Features extracted")

    # Classify and evaluate
    print("Start classification")
    skf = sklearn.model_selection.StratifiedKFold(n_splits=10)
    scores = []
    y = train_data[:, 1].astype(int)
    for fold_id, (train_indexes, validation_indexes) in enumerate(skf.split(train_data[:, 0], y)):
        # Print the fold number
        print("Fold %d" % (fold_id + 1))

        # Collect the data for this train/validation split
        train_features = [features[x] for x in train_indexes]
        train_labels = [y[x] for x in train_indexes]
        validation_features = [features[x] for x in validation_indexes]
        validation_labels = [y[x] for x in validation_indexes]

        # Classify and add the scores to be able to average later
        y_pred = classify(train_features, train_labels, validation_features)
        scores.append(evaluate(validation_labels, y_pred))

        # Print a newline
        print("")

    # Print the averaged score
    recall = sum([x[0] for x in scores]) / len(scores)
    print("Averaged total recall\t", recall)
    precision = sum([x[1] for x in scores]) / len(scores)
    print("Averaged total precision\t", precision)
    f_score = sum([x[2] for x in scores]) / len(scores)
    print("Averaged total f-score\t", f_score)
    print("")

    # TODO: Once you are done crafting your features and tuning your model, also test on the test set and report your
    # TODO: findings. How does the score differ from the validation score? And why do you think this is?

    # test_data = load_test_data()
    #
    # test_features = list(map(extract_features, test_data.data))
    # y_pred = classify(features, train_data.target, test_features)
    # print("results of test set")
    # evaluate(test_data.target, y_pred)
    # for feat in range(0, len(features[1])):
    #     features_holdout = copy.deepcopy(features)
    #     for doc in features_holdout:
    #         doc[feat] = 0
    #     y_pred = classify(features_holdout, train_data.target, test_features)
    #     evaluate(test_data.target, y_pred, feat)

# This piece of code is common practice in Python, is something like if "this file" is the main file to be ran, then
# execute this remaining piece of code. The advantage of this is that your main loop will not be executed when you
# import certain functions in this file in another file, which is useful in larger projects.
if __name__ == '__main__':
    main()
