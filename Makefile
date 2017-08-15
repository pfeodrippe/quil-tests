all:
	gifsicle --delay=5 --loop *.gif --colors 12 > output/anim.gif

example:
	ffmpeg -i output/disloco.mov -c:v libx264 -strict -2 -c:a aac -ar 44100 -r 30 -pix_fmt yuv420p -shortest output/out.mov
