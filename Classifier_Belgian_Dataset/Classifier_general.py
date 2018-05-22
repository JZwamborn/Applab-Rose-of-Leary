from ExtractFeatures import extract_features
from sklearn.ensemble import AdaBoostClassifier
from sklearn.metrics import confusion_matrix
from sklearn.feature_selection import VarianceThreshold
from imblearn.over_sampling import SMOTE
from Classify_utils import eight_labels, four_labels, evaluate, average_scores, makeOverview
from InverseDocumentFrequentizer import idf_vectorizer, generate_stemmed_bigrams, generate_bigrams, generate_trigrams, generate_character_trigrams, generate_functionword_trigrams
import Classifier_adaBoost
import Classifier_randomForest
import Classifier_Bayes
import Classifier_SVM
import Classifier_majority
import Classifier_logisticRegression
import Classifier_ecoc_svm


def classify(folds, classifier):
    scores = []
    for (train_data, train_labels, validation_data, validation_labels) in folds:

        idf_vectorizer(train_data)
        generate_stemmed_bigrams(train_data)
        print(generate_functionword_trigrams(train_data))
        # print(calc_document_frequencies_stemmed_words(train_data))

        # Features are extracted
        feature_matrix_train = list(map(extract_features, train_data))
        feature_matrix_validation = list(map(extract_features, validation_data))

        # Select features with enough variance
        # sel = VarianceThreshold(threshold=0.1)
        # sel = sel.fit(feature_matrix_train)
        # feature_matrix_train = sel.transform(feature_matrix_train)
        # feature_matrix_validation = sel.transform(feature_matrix_validation)

        # Oversampling is used to balance out dataset
        # feature_matrix_train, train_labels = SMOTE().fit_sample(feature_matrix_train, train_labels)

        validation_prediction = classifier.fit_and_predict(feature_matrix_train, train_labels, feature_matrix_validation)

        print(confusion_matrix(validation_labels, validation_prediction, labels=['LO', 'LB', 'RO', 'RB']))
        score = evaluate(validation_labels, validation_prediction)
        scores.append(score)

    average_scores(scores)
    return average_scores


def compare_performances():
    folds = eight_labels()
    for clf, label in zip([Classifier_randomForest, Classifier_adaBoost, Classifier_logisticRegression, Classifier_Bayes, Classifier_SVM, Classifier_majority],
                          ['Random Forest', 'Ada boost', 'Logistic Regression', 'Naive Bayes', 'Support Vector Machine',
                           'Majority vote']):
        print(label)
        classify(folds, clf)
        print("******************")


def predict_one_sample(sample, classifier):
    # extract features from the sample
    features = extract_features(sample)

    # let the classfier predict based on the sample
    prediction = classifier.predict(features)
    return prediction


def main():
    folds = eight_labels()
    classifier = Classifier_ecoc_svm.SVM()
    classify(folds, classifier)
    prediction = predict_one_sample("You are a stupid bitch", classifier)
    print(prediction)
    # compare_performances()

if __name__ == '__main__':
    main()