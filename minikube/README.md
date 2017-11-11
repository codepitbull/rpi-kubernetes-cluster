docker run -d -p 5000:5000 --restart=always --name registry registry:2
brew cask install minikube

minikube start
minikube addons enable ingress

eval $(minikube docker-env)    

kubectl exec -it shell-demo -- /bin/bash