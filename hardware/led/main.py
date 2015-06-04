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
	led_status = False
	check_timeout = 2
	pinOut = mraa.Gpio(13)
	pinOut.dir(mraa.DIR_OUT)
	pinAdc = mraa.Aio(5)

	while True:
		time.sleep(check_timeout)
		actions = network.get_actions()
		infos = network.get_infos()

		for action in actions:
			if action["name"] == "blink":
				blink_SOS(pinOut)
			elif action["name"] == "toggle_led":
				led_status = not led_status
				pinOut.write(led_status)
			elif action["name"] == "turn_on_with_delay":
				p = action["param"]
				time.sleep(p)
				led_status = True
				pinOut.write(1)
		for info in infos:
			if infos["name"] == "get_light_info":
				p = str(pinAdc.read())
				json_dict = {"name" : "get_light_info", "param" : p}
				network.send(json_dict)
			if infos["name"] == "toggle_led_info":
				p = str(led_status)
				json_dict = {"name" : "toggle_led_info", "param" : p}
				network.send(json_dict)

