all:
	gifsicle --delay=5 --loop *.gif --colors 8 > output/anim.gif
