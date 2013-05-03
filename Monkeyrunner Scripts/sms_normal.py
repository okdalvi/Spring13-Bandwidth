from com.android.monkeyrunner import MonkeyRunner, MonkeyDevice
device = MonkeyRunner.waitForConnection()
print "********************************************************************************"
print "Welcome to the Republic Wireless Automated Testing Suite"

print "********************************************************************************"

device.touch(241, 114, "DOWN_AND_UP")     # switch to sms tab
MonkeyRunner.sleep(0.5)

print "Want to make Wi-fi SMS (1 for YES 0 for NO)?"
f1 = int(raw_input())


if f1==1:
	print "Enter how many numbers???"
	n1 = int(raw_input())
	num_array_wifi = list()
	for p1 in range(n1):
 
		print "Enter the phone number:",(p1+1)
		num1 =(raw_input())
		num_array_wifi.append((num1))

		
print "Want to make Cellular SMS (1 for YES 0 for NO)?"
g1 = int(raw_input())

if g1==1:
	print "Enter how many numbers???"
	n2 = int(raw_input())
	num_array_cellular = list()
	for p2 in range(n2):
 
		print "Enter the phone number:",(p2+1)
		num2 =(raw_input())
		num_array_cellular.append((num2))
		

print "Want to make Normal SMS (1 for YES 0 for NO)?"
h1 = int(raw_input())

if h1==1:
	print "Enter how many numbers???"
	n3 = int(raw_input())
	num_array_cell = list()
	for p3 in range(n3):
 
		print "Enter the phone number:",(p3+1)
		num3 =(raw_input())
		num_array_cell.append((num3))		



   
	
if f1==1:
	print "SMS Automation WiFi started!!!"
	for q1 in range(n1):
		number= num_array_wifi[q1]
	
	
		device.touch(44, 296, "DOWN_AND_UP")       #go to text box
		MonkeyRunner.sleep(0.5)
	
		print(number)
		a=int(number[0])
		b=int(number[1])
		c=int(number[2])
		d=int(number[3])
		e=int(number[4])
		f=int(number[5])
		g=int(number[6])
		h=int(number[7])
		i=int(number[8])
		j=int(number[9])
		print "Dialling number!!!"
	
		i=0
		while i<10:
			z=int(number[i])
			if z==9:
				print "9 - Pressed 9!!!"
				device.press('KEYCODE_9','DOWN_AND_UP')
				MonkeyRunner.sleep(0.5)
				i=i+1
		 
			elif z==8:
				print "8 - Pressed 8!!!"
				device.press('KEYCODE_8','DOWN_AND_UP')
				MonkeyRunner.sleep(0.5)
				i=i+1
			
			elif z==7:
				print "7 - Pressed 7!!!"
				device.press('KEYCODE_7','DOWN_AND_UP')
				MonkeyRunner.sleep(0.5)
				i=i+1
		
			elif z==6:
				print "6 - Pressed 6!!!"
				device.press('KEYCODE_6','DOWN_AND_UP')
				MonkeyRunner.sleep(0.5)
				i=i+1
			elif z==5:
				print "5 - Pressed 5!!!"
				device.press('KEYCODE_5','DOWN_AND_UP')
				MonkeyRunner.sleep(0.5)
				i=i+1
			elif z==4:
				print "4 - Pressed 4!!!"
				device.press('KEYCODE_4','DOWN_AND_UP')
				MonkeyRunner.sleep(0.5)
				i=i+1
			elif z==3:
				print "3 - Pressed 3!!!"
				device.press('KEYCODE_3','DOWN_AND_UP')
				MonkeyRunner.sleep(0.5)
				i=i+1
			elif z==2:
				print "2 - Pressed 2!!!"
				device.press('KEYCODE_2','DOWN_AND_UP')
				MonkeyRunner.sleep(0.5)
				i=i+1
			elif z==1:
				print "1 - Pressed 1!!!"
				device.press('KEYCODE_1','DOWN_AND_UP')
				MonkeyRunner.sleep(0.5)
				i=i+1
			elif z==0:
				print "0 - Pressed 0!!!"
				device.press('KEYCODE_0','DOWN_AND_UP')
				MonkeyRunner.sleep(0.5)
				i=i+1
		print "SMS number",(q1+1) 
		print "in progress!!!"
		MonkeyRunner.sleep(0.5)
		device.touch(247, 652, "DOWN_AND_UP")       #press sms key
		MonkeyRunner.sleep(5)
		       
		
		print "SMS number",(q1+1) 
		print "completed!!!"
		MonkeyRunner.sleep(10)
		
		
if g1==1:
	print "SMS Automation Cellular started!!!"
	for q2 in range(n2):
		number= num_array_cellular[q2]
	
	
		device.touch(44, 296, "DOWN_AND_UP")       #go to text box
		MonkeyRunner.sleep(0.5)
	
		print(number)
		a=int(number[0])
		b=int(number[1])
		c=int(number[2])
		d=int(number[3])
		e=int(number[4])
		f=int(number[5])
		g=int(number[6])
		h=int(number[7])
		i=int(number[8])
		j=int(number[9])
		print "Dialling number!!!"
	
		i=0
		while i<10:
			z=int(number[i])
			if z==9:
				print "9 - Pressed 9!!!"
				device.press('KEYCODE_9','DOWN_AND_UP')
				MonkeyRunner.sleep(0.5)
				i=i+1
		 
			elif z==8:
				print "8 - Pressed 8!!!"
				device.press('KEYCODE_8','DOWN_AND_UP')
				MonkeyRunner.sleep(0.5)
				i=i+1
			
			elif z==7:
				print "7 - Pressed 7!!!"
				device.press('KEYCODE_7','DOWN_AND_UP')
				MonkeyRunner.sleep(0.5)
				i=i+1
		
			elif z==6:
				print "6 - Pressed 6!!!"
				device.press('KEYCODE_6','DOWN_AND_UP')
				MonkeyRunner.sleep(0.5)
				i=i+1
			elif z==5:
				print "5 - Pressed 5!!!"
				device.press('KEYCODE_5','DOWN_AND_UP')
				MonkeyRunner.sleep(0.5)
				i=i+1
			elif z==4:
				print "4 - Pressed 4!!!"
				device.press('KEYCODE_4','DOWN_AND_UP')
				MonkeyRunner.sleep(0.5)
				i=i+1
			elif z==3:
				print "3 - Pressed 3!!!"
				device.press('KEYCODE_3','DOWN_AND_UP')
				MonkeyRunner.sleep(0.5)
				i=i+1
			elif z==2:
				print "2 - Pressed 2!!!"
				device.press('KEYCODE_2','DOWN_AND_UP')
				MonkeyRunner.sleep(0.5)
				i=i+1
			elif z==1:
				print "1 - Pressed 1!!!"
				device.press('KEYCODE_1','DOWN_AND_UP')
				MonkeyRunner.sleep(0.5)
				i=i+1
			elif z==0:
				print "0 - Pressed 0!!!"
				device.press('KEYCODE_0','DOWN_AND_UP')
				MonkeyRunner.sleep(0.5)
				i=i+1
		print "SMS number",(q2+1) 
		print "in progress!!!"
		
		MonkeyRunner.sleep(0.5)
		device.touch(239, 762, "DOWN_AND_UP")       #press sms key
		MonkeyRunner.sleep(5)
		print "SMS number",(q2+1) 
		print "completed!!!"
		MonkeyRunner.sleep(10)
		
if h1==1:
	print "SMS Automation MS started!!!"
	for q3 in range(n3):
		number= num_array_cell[q3]
	
	
		device.touch(44, 296, "DOWN_AND_UP")       #go to text box
		MonkeyRunner.sleep(0.5)
	
		print(number)
		a=int(number[0])
		b=int(number[1])
		c=int(number[2])
		d=int(number[3])
		e=int(number[4])
		f=int(number[5])
		g=int(number[6])
		h=int(number[7])
		i=int(number[8])
		j=int(number[9])
		print "Dialling number!!!"
	
		i=0
		while i<10:
			z=int(number[i])
			if z==9:
				print "9 - Pressed 9!!!"
				device.press('KEYCODE_9','DOWN_AND_UP')
				MonkeyRunner.sleep(0.5)
				i=i+1
		 
			elif z==8:
				print "8 - Pressed 8!!!"
				device.press('KEYCODE_8','DOWN_AND_UP')
				MonkeyRunner.sleep(0.5)
				i=i+1
			
			elif z==7:
				print "7 - Pressed 7!!!"
				device.press('KEYCODE_7','DOWN_AND_UP')
				MonkeyRunner.sleep(0.5)
				i=i+1
		
			elif z==6:
				print "6 - Pressed 6!!!"
				device.press('KEYCODE_6','DOWN_AND_UP')
				MonkeyRunner.sleep(0.5)
				i=i+1
			elif z==5:
				print "5 - Pressed 5!!!"
				device.press('KEYCODE_5','DOWN_AND_UP')
				MonkeyRunner.sleep(0.5)
				i=i+1
			elif z==4:
				print "4 - Pressed 4!!!"
				device.press('KEYCODE_4','DOWN_AND_UP')
				MonkeyRunner.sleep(0.5)
				i=i+1
			elif z==3:
				print "3 - Pressed 3!!!"
				device.press('KEYCODE_3','DOWN_AND_UP')
				MonkeyRunner.sleep(0.5)
				i=i+1
			elif z==2:
				print "2 - Pressed 2!!!"
				device.press('KEYCODE_2','DOWN_AND_UP')
				MonkeyRunner.sleep(0.5)
				i=i+1
			elif z==1:
				print "1 - Pressed 1!!!"
				device.press('KEYCODE_1','DOWN_AND_UP')
				MonkeyRunner.sleep(0.5)
				i=i+1
			elif z==0:
				print "0 - Pressed 0!!!"
				device.press('KEYCODE_0','DOWN_AND_UP')
				MonkeyRunner.sleep(0.5)
				i=i+1
		print "SMS",(q3+1) 
		print "in progress!!!"
		
		MonkeyRunner.sleep(0.5)
		device.touch(258, 539, "DOWN_AND_UP")       #press sms key
		MonkeyRunner.sleep(5)
		print "SMS number",(q3+1) 
		print "completed!!!"
		MonkeyRunner.sleep(10)

print "********************************************************************************"	
print "Test Suite Completed!!!"

print "********************************************************************************"
import subprocess
 
html = "http://192.168.1.12/Graphs/call-graph-2.html"
ChromePath = r'C:\Users\omi\AppData\Local\Google\Chrome\Application\Chrome.exe'
subprocess.Popen("%s %s" % (ChromePath, html))

print "********************************************************************************"	
print "GRAPH VIEWED!!!"

print "********************************************************************************"