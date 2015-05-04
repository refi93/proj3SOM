%load data
load iris %SOM_data

%aby sme triedu nepouzivali pri treningu
data = data(1:4,:);

csvwrite('iris.csv',data');
[n_input,n_data] = size(data);

neurons_r = 10;
neurons_c = 10;

w = rand(neurons_r, neurons_c, n_input);
alpha = 0.05;
lambda_initial = 15;
lambda = lambda_initial;
lambda_final = 1;
ep_max = 100;

for ep=1:ep_max
   ep
   data = data(:,randperm(n_data));
   %update lambda
   lambda=lambda_initial*(lambda_final/lambda_initial)^(ep/ep_max);
   lambda
   avg_win_score = 0;
   for j=1:n_data
      x = data(:, j);
      winner_r = 0;
      winner_c = 0;
      winner_score = realmax;
      for r=1:neurons_r
         for c=1:neurons_c
            score = norm(x'- reshape(w(r,c,:), 1, n_input));
            if (score < winner_score)
               winner_r = r;
               winner_c = c;
               winner_score = score;
            end
         end
      end
      avg_win_score = avg_win_score + winner_score;
      %update vah
      i_star = [winner_r, winner_c];
      for r=1:neurons_r
         for c=1:neurons_c
            i = [r,c];
            %size(i)
            %size(i_star)
            delta_w = alpha * h(i_star, i, lambda)*(reshape(x, 1, 1, n_input) - w(r,c,:));
            w(r,c,:) = w(r,c,:) + delta_w;
         end
      end
   end
   avg_win_score = avg_win_score / n_data;
end


%zobrazenie
close all
plot3(data(1,:),data(2,:),data(3,:), 'o')
grid
hold on

for i=1:size(w,1)
        plot3(w(i,:,1),w(i,:,2),w(i,:,3),'r')
end

for i=1:size(w,2)
        plot3(w(:,i,1),w(:,i,2),w(:,i,3),'r')
end
hold off


%komponenty
figure;
grid off
velk = ceil(sqrt(n_input));
for i=1:n_input
    subplot(velk,velk,i);
    imagesc(w(:,:,i),[-10,10])
end