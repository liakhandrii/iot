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

	while True:
		time.sleep(check_timeout)
		actions = network.get_actions()
		print actions
		if not actions:
			continue

		for action in actions:
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
			elif action["action"] == "get_light_info":
				p = str(pinAdc.read() / 1024.0)
				json_dict = {"action" : "get_light_info", "param" : p}
				network.send(json_dict)
			elif action["action"] == "toggle_led_info":
				p = str(led_status)
				json_dict = {"action" : "toggle_led_info", "param" : p}
				network.send(json_dict)

