<!DOCTYPE html>
<html>
<head>
    <title>Visitor Counter</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f5f5f5;
            text-align: center;
            padding-top: 100px;
        }
        .container {
            background-color: white;
            border-radius: 10px;
            box-shadow: 0 0 10px gray;
            width: 400px;
            margin: auto;
            padding: 30px;
        }
        h1 {
            color: #2c3e50;
        }
        h2 {
            color: #16a085;
        }
    </style>
</head>
<body>
    <div class="container">
        <h1>Welcome to My College Web Page</h1>
        <h2>Visitor Counter</h2>
        <?php
            // File to store visitor count
            $file = "counter.txt";

            // Check if file exists
            if (!file_exists($file)) {
                $count = 0;
            } else {
                $count = (int)file_get_contents($file);
            }

            // Increment the count
            $count++;

            // Write the new count to file
            file_put_contents($file, $count);

            // Display visitor count
            echo "<p>You are visitor number: <strong>$count</strong></p>";
        ?>
    </div>
</body>
</html>
