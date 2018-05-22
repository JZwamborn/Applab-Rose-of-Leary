import socket
import sys
import Classifier_general
import Classifier_ecoc_svm

import Classify_utils

HOST = '192.168.0.104'
PORT = 8888

folds = Classify_utils.eight_labels()
classifier = Classifier_ecoc_svm.SVM()
Classifier_general.classify(folds, classifier)
prediction = Classifier_general.predict_one_sample("You are a stupid bitch", classifier)
print(prediction)

s = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
print('socket created')

# Bind socket to Host and Port
try:
    s.bind((HOST, PORT))
except socket.error as err:
    print('Bind Failed, Error Code: ' + str(err[0]) + ', Message: ' + err[1])
    sys.exit()

print('Socket Bind Success!')


# listen(): This method sets up and start TCP listener.
s.listen(10)
print('Socket is now listening')


while 1:
    conn, addr = s.accept()
    print('Connect with ' + addr[0] + ':' + str(addr[1]))
    data = conn.recv(64)
    print(data)
    data = str(Classifier_general.predict_one_sample(str(data), classifier))
    print("sending: " + str(data))
    conn.send(data.encode())
    conn.close()
s.close()

