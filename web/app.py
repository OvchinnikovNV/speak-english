from flask import Flask
from flask import request
from flask import render_template
import os

app = Flask(__name__)


@app.route("/")
def index():
    return render_template("index.html")



@app.route("/game", methods=['POST', 'GET'])
def game():
    if request.method == "POST":
        f = request.files['audio_data']
        with open('audio.wav', 'wb') as audio:
            f.save(audio)
        print('file uploaded successfully')

        return render_template('game.html', request="POST")
    else:
        return render_template("game.html")



if __name__ == "__main__":
    app.run(debug=True)
