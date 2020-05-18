import datetime
f = open("rodna_cisla_good.txt", "w")

def isValidBirth(stringNumber):
    stringNumber = stringNumber.replace("/","")
    stringNumber = stringNumber.replace("\n","")
    length = len(stringNumber)
    if not ((length==9 or length==10) and stringNumber.isnumeric()):
        return False

    year = int(stringNumber[0:2])
    month = int(stringNumber[2:4])
    day = int(stringNumber[4:6])

    if(month>50):
        month-=50
    if(month>20):
        month-=20;

    try:
        datetime.datetime(year=year, month=month, day=day)
    except:
        return False;

    return length==9 or int(stringNumber)%11==0

with open ("rodna_cisla.txt", 'rt') as myfile:
    for myline in myfile:
        if(isValidBirth(myline)):
            f.write(myline)
f.close()