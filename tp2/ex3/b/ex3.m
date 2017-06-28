function ex3()



apostadores();
%apostas();




end


function apostadores()

names = {'apostadores1000.txt', 'apostadores10000.txt', 'apostadores100000.txt', 'apostadores1000000.txt'};
N = [1000, 10000, 100000, 1000000];

ApostadoresInitialLength = zeros(1,4);
Naturalfiles = dir('.\apostadores\natural');
index = 1;
for i = 1 : length(Naturalfiles)
    file_name = Naturalfiles(i).name; 
        if(strcmp(file_name,'.') || strcmp(file_name,'..')) 
            continue;
        end
    ApostadoresInitialLength(index) = Naturalfiles(i).bytes; 
    index = index +1;
end

ApostadoresFinalLength = zeros(1,4);
Compressedfiles = dir('.\apostadores\compressed');
index = 1;
for i = 1 : length(Naturalfiles)
    file_name = Compressedfiles(i).name; 
        if(strcmp(file_name,'.') || strcmp(file_name,'..')) 
            continue;
        end
    ApostadoresCompressionRatio(index) = (1 - Compressedfiles(i).bytes / ApostadoresInitialLength(index)) * 100;
    index = index +1;
end

xt = zeros(1,4);
yt = zeros(1,4);
figure
for i=1 : length(N)
    plot(N(i), ApostadoresCompressionRatio(i),'.', 'markersize', 30);
    ylabel('Removed ratio');
    xlabel('Number of lines');
    xt(i) = N(i);
    yt(i) = ApostadoresCompressionRatio(i);
    hold on;
end

text(xt, yt, names);

end

function apostas()
names = {'apostas1000.txt', 'apostas10000.txt', 'apostas100000.txt', 'apostas1000000.txt'};
N = [1000, 10000, 100000, 1000000];

ApostasInitialLength = zeros(1,4);
Naturalfiles = dir('.\apostas\natural');
index = 1;
for i = 1 : length(Naturalfiles)
    file_name = Naturalfiles(i).name; 
        if(strcmp(file_name,'.') || strcmp(file_name,'..')) 
            continue;
        end
    ApostasInitialLength(index) = Naturalfiles(i).bytes; 
    index = index +1;
end

ApostasRemovedRate = zeros(1,4);
Compressedfiles = dir('.\apostas\compressed');
index = 1;
for i = 1 : length(Naturalfiles)
    file_name = Compressedfiles(i).name; 
        if(strcmp(file_name,'.') || strcmp(file_name,'..')) 
            continue;
        end
    ApostasRemovedRate(index) = (1 - Compressedfiles(i).bytes / ApostasInitialLength(index)) * 100;
    index = index +1;
end

xt = zeros(1,4);
yt = zeros(1,4);
figure
for i=1 : length(N)
    plot(N(i), ApostasRemovedRate(i),'.', 'markersize', 30);   
    ylabel('Removed rate');
    xlabel('Number of lines');
    xt(i) = N(i);
    yt(i) = ApostasRemovedRate(i);
    hold on;
end

text(xt, yt, names);

end