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
    try:
        response = urllib2.urlopen(req, json.dumps(data))
    except Exception as e:
        e = 1


def get_actions():
    if MY_ID == '':
        return None
    tasks = json.load(urllib2.urlopen("https://ahome.azure-mobile.net/tables/task"))
    task_list = list()
    for item in tasks:
        if item['id'] == MY_ID:
            task_list.append(item)
    return task_list


def test_action():
    with open('task.sample.json') as data_file:
        data = json.load(data_file)
    task_list = list()
    for item in data:
        if item['id'] == MY_ID:
            task_list.append(json.loads(item['action']))

    return task_list

if MY_ID == "":
    MY_ID = read_json_from_file()['id']