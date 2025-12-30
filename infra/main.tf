provider "aws" {
  region = "sa-east-1"
}

resource "aws_security_group" "securitygroup" {
  name        = "ec2-securitygroup"
  description = "Ingress Http and SSH and Egress to anywhere "

  ingress {
    from_port   = 80
    to_port     = 80
    protocol    = "tcp"
    cidr_blocks = ["0.0.0.0/0"]
  }

  ingress {
    from_port   = 22
    to_port     = 22
    protocol    = "tcp"
    cidr_blocks = ["0.0.0.0/0"]
  }

  egress {
    from_port   = 0
    to_port     = 65535
    protocol    = "tcp"
    cidr_blocks = ["0.0.0.0/0"]
  }
}

resource "aws_key_pair" "keypair" {
  key_name        = "terraform-keypair"
  public_key = file("id_ed25519.pub")
}

resource "aws_instance" "servidor" {
  ami           = "ami-0484b27e9143d1299"
  instance_type = "t2.micro"
  key_name      = aws_key_pair.keypair.key_name
  user_data     = file("user_data.sh")
  vpc_security_group_ids = [aws_security_group.securitygroup.id]
}