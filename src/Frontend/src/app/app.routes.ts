import { Routes } from '@angular/router';
import { LoginComponent } from './components/login/login.component';
import { AtividadeListComponent } from './components/atividade-list/atividade-list.component';
import { AtividadeFormComponent } from './components/atividade-form/atividade-form.component';
import { AtividadeDetailComponent } from './components/atividade-detail/atividade-detail.component';

export const routes: Routes = [
  { path: '', redirectTo: 'login', pathMatch: 'full' },
  { path: 'login', component: LoginComponent },
  { path: 'atividades', component: AtividadeListComponent },
  { path: 'atividades/nova', component: AtividadeFormComponent },
  { path: 'atividades/:id', component: AtividadeDetailComponent }
];
