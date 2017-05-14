%
% ISEL - Instituto Superior de Engenharia de Lisboa.
% LEIC - Licenciatura em Engenharia Informatica e de Computadores.
% SM - Sistemas Multim�dia.
%
%
% histFile.m
% Funcao que mostra o histograma 1D de determinado ficheiro.
%
% Recebe:		filename    - nome do ficheiro de entrada.
%               nBins       - numero de pontos centrais do histograma.

function histFile(path, filename, nBins )

% Read file to a vector.
x = file2Vector(path);

% Check for the number of bins.
if nargin==1
    % Use default (ASCII).
    nBins = 256;
end

% Display histogram.
hist( x, nBins );
grid on; title(filename);

% Frequ�ncia de ocorr�ncia dos s�mbolos
f = hist( x, 256 );

uniqueValues = length(hist(x,unique(x)));

% Probabilidades dos s�mbolos.
p =  f / sum(f);
p( p==0 ) = 1;

% Calcular a entropia do ficheiro (estimada ao n�vel do s�mbolo).
H = - sum( p .* log2(p) )

legend(['H(X)= ' num2str(H)]);
xlabel(['distinct symbols = ' num2str(uniqueValues)])

[s,e] = strtok(filename, '.');
print( gcf, [s, '.png'], '-dpng'  );

return;



