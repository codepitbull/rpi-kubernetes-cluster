# Get the Hypriot image and flash it on your SDs Image

``` 
wget https://github.com/hypriot/image-builder-rpi/releases/tag/v1.6.0
```

For flashing use the following sequence
Run the next command before and after inserting the SD-card into your mac and note the newly appeared device:
```
diskutil list
```

```
sudo diskutil umount /dev/<devicename>
sudo dd if=hypriotos-rpi-v1.6.0.img of=/dev/r<devicename> bs=1m
```

# Ethernet config
Adjust _/etc/network/interfaces.d/eth0_ on each machine, adjust ip-address (51,52,53,54)

```
allow-hotplug eth0
auto eth0
iface eth0 inet static
address 192.168.2.51
netmask 255.255.255.0
gateway 192.168.2.2
dns-nameservers 8.8.8.8
```

# pre install things to do

##Create unique machine id (TODO: automate this!!!)

```
ansible rcluster --become -m shell --args "dbus-uuidgen > /etc/machine-id"
``` 

Disable password auth in  _/etc/ssh/sshd_config_ by adding 

```
PasswordAuthentication no
```

Add your public key (id_rsa.pub) to .ssh/authorized_keys.
Change hostname in _/boot/device-init.yaml_ (nr1,nr2,nr3,nr4)

# Setup

```
ansible-playbook setup.yml -i hosts
```

# Reset

```
ansible-playbook reset.yml -i hosts
```
