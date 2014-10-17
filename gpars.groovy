import groovyx.gpars.*

def vector = []
def results = []

for(i in 0..100000)
	vector << i

def start = new Date()

GParsPool.withPool{
	vector.findAllParallel{isPrime(it)}.eachParallel{
		results << it
	}
}

def elapsed = new Date().getTime() - start.getTime()

println "Done in "+ elapsed

start = new Date()

results = []

vector.findAll{isPrime(it)}.each{
	results << it
}

elapsed = new Date().getTime() - start.getTime()

println "Done in " + elapsed

def isPrime(def number){
	for(i in 2..(number-1)){
		if(number%i==0){
			return false;
		}
	}
	return true;
}