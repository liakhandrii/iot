import urllib2
import json

MY_ID = ""


def read_json_from_file():
    with open('led_config.json') as data_file:
        return json.load(data_file)


def first_connect():
    data = read_json_from_file()

    MY_ID = data['id']

    req = urllib2.Request('http://ahome.azure-mobile.net/tables/devices')
    req.add_header('Content-Type', 'application/json')

    response = urllib2.urlopen(req, json.dumps(data))


def get_actions():
    if MY_ID == '':
        return None
    tasks = json.load(urllib2.urlopen("http://ahome.azure-mobile.net/tables/task"))
    for item in tasks:
        if item['id'] == MY_ID:
            return item['actions_list']
    return None


if MY_ID == "":
    MY_ID = read_json_from_file()['id']

first_connect()
print(get_actions())