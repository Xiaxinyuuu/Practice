f=@(x)2*x(1)^2*-1 - 2*x(2)^2 + 2*x(1)*x(2) + 4*x(1) + 6*x(2);
A = [1 1;1 5];
b = [2;5];
x0 = [1 0];
[x,fval] = fmincon(f,x0,A,b,[],[],[0 0],[]);
max = -fval;