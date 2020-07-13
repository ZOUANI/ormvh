import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Subdivision } from 'app/shared/model/subdivision.model';
import { SubdivisionService } from './subdivision.service';
import { SubdivisionComponent } from './subdivision.component';
import { SubdivisionDetailComponent } from './subdivision-detail.component';
import { SubdivisionUpdateComponent } from './subdivision-update.component';
import { SubdivisionDeletePopupComponent } from './subdivision-delete-dialog.component';
import { ISubdivision } from 'app/shared/model/subdivision.model';

@Injectable({ providedIn: 'root' })
export class SubdivisionResolve implements Resolve<ISubdivision> {
  constructor(private service: SubdivisionService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<ISubdivision> {
    const id = route.params['id'] ? route.params['id'] : null;
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<Subdivision>) => response.ok),
        map((subdivision: HttpResponse<Subdivision>) => subdivision.body)
      );
    }
    return of(new Subdivision());
  }
}

export const subdivisionRoute: Routes = [
  {
    path: '',
    component: SubdivisionComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'ormvahApp.subdivision.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: SubdivisionDetailComponent,
    resolve: {
      subdivision: SubdivisionResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'ormvahApp.subdivision.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: SubdivisionUpdateComponent,
    resolve: {
      subdivision: SubdivisionResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'ormvahApp.subdivision.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: SubdivisionUpdateComponent,
    resolve: {
      subdivision: SubdivisionResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'ormvahApp.subdivision.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const subdivisionPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: SubdivisionDeletePopupComponent,
    resolve: {
      subdivision: SubdivisionResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'ormvahApp.subdivision.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
