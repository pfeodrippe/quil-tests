all:
	gifsicle --delay=5 --loop *.gif --colors 12 > output/anim.gif
