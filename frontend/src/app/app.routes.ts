import { Routes } from '@angular/router';
import { RegistroComponent } from './pages/registro/registro.component';
import { InicioSesionComponent } from './pages/inicio-sesion/inicio-sesion.component';
import { IndiceComponent } from './pages/indice/indice.component';
import { UsuarioComponent } from './pages/usuario/usuario.component';
import { authGuard } from './services/auth.guard';
import { ProyectoComponent } from './pages/proyecto/proyecto.component';
import { BitacoraComponent } from './pages/proyecto/bitacora/bitacora.component';
import { ProyectoFormularioComponent } from './pages/proyecto/proyecto-formulario.component';
import { ProyectoDetalleComponent } from './pages/proyecto/proyecto-detalle.component';

export const routes: Routes = [
    { path: '', redirectTo: '/indice', pathMatch: 'full' },
    { path: 'registro', component: RegistroComponent },
    { path: 'inicioSesion', component: InicioSesionComponent },
    { path: 'indice', component: IndiceComponent, canActivate: [authGuard] },
    { path: 'usuario', component: UsuarioComponent, canActivate: [authGuard] }
    ,{ path: 'proyecto', component: ProyectoComponent, canActivate: [authGuard] }
    ,{ path: 'proyecto/nuevo', component: ProyectoFormularioComponent, canActivate: [authGuard] }
    ,{ path: 'proyecto/:idProyecto/editar', component: ProyectoFormularioComponent, canActivate: [authGuard] }
    ,{ path: 'proyecto/:idProyecto', component: ProyectoDetalleComponent, canActivate: [authGuard] }
    ,{ path: 'proyecto/:idProyecto/bitacora', component: BitacoraComponent, canActivate: [authGuard] }
];
