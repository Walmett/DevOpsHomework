from flask import Flask, request, jsonify
import psycopg2

app = Flask(__name__)

DB_HOST = 'db'
DB_NAME = 'mydatabase'
DB_USER = 'postgres'
DB_PASSWORD = 'password'


def get_db_connection():
    conn = psycopg2.connect(
        host=DB_HOST,
        database=DB_NAME,
        user=DB_USER,
        password=DB_PASSWORD
    )
    return conn


@app.route('/add_data', methods=['POST'])
def add_data():
    data = request.get_json()
    conn = get_db_connection()
    cursor = conn.cursor()
    cursor.execute("CREATE TABLE IF NOT EXISTS data (id SERIAL PRIMARY KEY, name VARCHAR(255), value INTEGER);")
    cursor.execute(
        "INSERT INTO data (name, value) VALUES (%s, %s)",
        (data['name'], data['value'])
    )
    conn.commit()
    cursor.close()
    conn.close()
    return jsonify({'message': 'Add data successfully'}), 201


@app.route('/get_data', methods=['GET'])
def get_data():
    conn = get_db_connection()
    cursor = conn.cursor()
    cursor.execute("SELECT * FROM data")
    data = cursor.fetchall()
    cursor.close()
    conn.close()
    return jsonify(data), 200


if __name__ == '__main__':
    app.run(host='0.0.0.0', debug=True)
