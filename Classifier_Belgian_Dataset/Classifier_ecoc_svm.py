from sklearn import svm
from sklearn.multiclass import OutputCodeClassifier
from sklearn.svm import LinearSVC


class SVM:
    svm_ecoc_classifier = None

    def __init__(self):
        self.svm_ecoc_classifier = OutputCodeClassifier(LinearSVC(random_state=0), code_size=2, random_state=0)

    def fit_and_predict(self, feature_matrix_train, train_labels, feature_matrix_validation):
        self.svm_ecoc_classifier.fit(feature_matrix_train, train_labels)
        validation_prediction = self.svm_ecoc_classifier.predict(feature_matrix_validation)
        return validation_prediction

    def predict(self, feature_matrix_validation):
        validation_prediction = self.svm_ecoc_classifier.predict([feature_matrix_validation])
        return list(validation_prediction)