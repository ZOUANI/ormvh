import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { NatureCourrier } from 'app/shared/model/nature-courrier.model';
import { NatureCourrierService } from './nature-courrier.service';
import { NatureCourrierComponent } from './nature-courrier.component';
import { NatureCourrierDetailComponent } from './nature-courrier-detail.component';
import { NatureCourrierUpdateComponent } from './nature-courrier-update.component';
import { NatureCourrierDeletePopupComponent } from './nature-courrier-delete-dialog.component';
import { INatureCourrier } from 'app/shared/model/nature-courrier.model';

@Injectable({ providedIn: 'root' })
export class NatureCourrierResolve implements Resolve<INatureCourrier> {
  constructor(private service: NatureCourrierService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<INatureCourrier> {
    const id = route.params['id'] ? route.params['id'] : null;
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<NatureCourrier>) => response.ok),
        map((natureCourrier: HttpResponse<NatureCourrier>) => natureCourrier.body)
      );
    }
    return of(new NatureCourrier());
  }
}

export const natureCourrierRoute: Routes = [
  {
    path: '',
    component: NatureCourrierComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'ormvahApp.natureCourrier.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: NatureCourrierDetailComponent,
    resolve: {
      natureCourrier: NatureCourrierResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'ormvahApp.natureCourrier.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: NatureCourrierUpdateComponent,
    resolve: {
      natureCourrier: NatureCourrierResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'ormvahApp.natureCourrier.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: NatureCourrierUpdateComponent,
    resolve: {
      natureCourrier: NatureCourrierResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'ormvahApp.natureCourrier.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const natureCourrierPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: NatureCourrierDeletePopupComponent,
    resolve: {
      natureCourrier: NatureCourrierResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'ormvahApp.natureCourrier.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
