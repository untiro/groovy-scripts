def user = "admin"
def password = "admin123"
def ARTIFACTID = "zvirinsky"
def BUILD_NUMBER = "33"
def GROUPID = "Task-8"
def VERSIONID = "1.0"
def baseURL = "http://192.168.1.223:8081"
def repositoryid = "MNT-maven2-hosted-releases"
String basicAuthString = "Basic " + "$user:$password".bytes.encodeBase64().toString()