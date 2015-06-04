import mraa
import network
import time


def blink(pin, duration):
	pin.write(1)
	time.sleep(duration)
	pin.write(0)
	time.sleep(duration)


def blink_SOS(pin):
	for i in range(3):
		blink(pin, 0.25)
	for i in range(3):
		blink(pin, 1)
	for i in range(3):
		blink(pin, 0.25)


if __name__ == "__main__":
	network.first_connect()
	led_status = False
	check_timeout = 1
	pinOut = mraa.Gpio(5)
	pinOut.dir(mraa.DIR_OUT)
	pinAdc = mraa.Aio(0)


	performed_tasks = []


	dev_id = network.get_my_id()


	while True:
		time.sleep(check_timeout)
		actions = network.get_actions()
		if not actions:
			continue

		for action in actions:
			if action["id"] in performed_tasks:
				continue
			if action["action"] == "blink":
				blink_SOS(pinOut)
			elif action["action"] == "toggle_led":
				led_status = not led_status
				pinOut.write(led_status)
			elif action["action"] == "turn_on_with_delay":
				p = float(action["param"])
				time.sleep(p)
				led_status = True
				pinOut.write(1)
		p = str(pinAdc.read() / 1024.0)
		json_dict = {"dev_id" : dev_id, "name" : "get_light_info", "param" : p}
		network.send(json_dict)
		p = str(led_status)
		json_dict = {"dev_id" : dev_id, "name" : "toggle_led_info", "param" : p}
		network.send(json_dict)
		performed_tasks.append(action["id"])

