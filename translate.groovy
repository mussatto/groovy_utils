@Grab(group='org.codehaus.groovy.modules.http-builder', module='http-builder', version='0.7' )
import groovyx.net.http.*
import static groovyx.net.http.ContentType.*
import static groovyx.net.http.Method.*
import groovy.json.JsonSlurper

def i18n_symbols = ["am","az","be","bg","bn","ca","crs","cs","da","de","el","es","et","fa","fi","fr","hi","hr","hu","hy","id","is","it","iw","ja","ka","kh","kk","ko","kri","lo","lr","lt","lv","mg","mk","mn","ms","mt","my","ne","nl","no","ny","pl","ps","pt_BR","pt","ro","ru","si","sk","sl","so","sq","sr_ME","sr","st","sv","sw","tg","th","tk","tl","tn","to","tr","uk","ur","uz","vi","zh_CN"]
//def i18n_symbols = ["am","az"] // in case of testing use these

def words = ["Maps","Shopping", "Books", "More", "Applications"]
def from = "en"


def getTranslation(String word, String languageFrom, String languageTo){
	String url = "http://translate.google.com/translate_a/t?client=j&text=${word}&hl=en&sl=${languageFrom}&tl=${languageTo}"
	def http = new groovyx.net.http.HTTPBuilder(url)
	def translate = "not found"
	http.request( GET, JSON ) {
	  response.success = { resp, json ->
	    translate = json.get('sentences').get(0).get('trans')
		}
	}


	return translate
}

def doMassTranslate(){
	println "Starting mass translator"

	

	for( i18n_symbol in i18n_symbols){
		def file = createFile("messages_${i18n_symbol}.properties")

		for( word in words){
			
			printToFile("default.websearch.${word}="+getTranslation(word, from , i18n_symbol), file)

		}
	}
}

def createFile(String filename){
	println "creating file "+ filename

	def file = new File(filename)
	return file
}

def printToFile(String word, def file){
	println "printed in file "+word
	file << word +"\n"
}

doMassTranslate()