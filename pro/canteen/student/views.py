# -*- coding: utf-8 -*-
from __future__ import unicode_literals

from django.shortcuts import render
import csv
# Create your views here.
lst=["Item.....................Cost"," ","1. Samosa...............Rs.10","2. Burger.................Rs.20","3. Momo.................Rs.30","4. Biscuit................Rs.25","5. Kurkure..............Rs.20","6. ColdDrink..........Rs.15","7. Coffee................Rs.25"]

def myfirstpage(request):
	return render(request,'FirstPage.html')



def show(request):
	return render(request,"menu.html",{"lst":lst})

def accept(request):
	return render(request,"accepted.html")

def order(request):
	L=[]
	if(request.method=='POST'):
		dic=request.POST
		with open("stud.csv",'a') as st:
			val=csv.writer(st)
			for x in dic:
				if x=="i1":
					L.append(dic[x])
				if x=="q1":
					L.append(dic[x])
			val.writerow(L)
			print(L)
	return render(request,"accepted.html",{"lst":L})


