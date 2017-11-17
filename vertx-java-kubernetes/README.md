kubectl config use-context minikube
eval $(minikube docker-env)  

# Create Hazelcast service
kubectl create -f hazelcast.yaml

# Create Deployment and scale
kubectl create -f deployment.yaml
kubectl scale --replicas=2 -f deployment.yaml

# Publish via Ingress  
kubectl create -f ingress.yaml

kubectl expose deployment vertx-java-demo

mvn clean package docker:build

kubectl delete pods <pod> --grace-period=0 --force

kubectl exec -it shell-demo -- /bin/bash
