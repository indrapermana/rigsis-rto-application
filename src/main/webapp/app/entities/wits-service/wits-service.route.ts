import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { of } from 'rxjs';
import { map } from 'rxjs/operators';
import { WitsService } from 'app/shared/model/wits-service.model';
import { WitsServiceService } from './wits-service.service';
import { WitsServiceComponent } from './wits-service.component';
import { WitsServiceDetailComponent } from './wits-service-detail.component';
import { WitsServiceUpdateComponent } from './wits-service-update.component';
import { WitsServiceDeletePopupComponent } from './wits-service-delete-dialog.component';
import { IWitsService } from 'app/shared/model/wits-service.model';

@Injectable({ providedIn: 'root' })
export class WitsServiceResolve implements Resolve<IWitsService> {
    constructor(private service: WitsServiceService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(map((witsService: HttpResponse<WitsService>) => witsService.body));
        }
        return of(new WitsService());
    }
}

export const witsServiceRoute: Routes = [
    {
        path: 'wits-service',
        component: WitsServiceComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'WitsServices'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'wits-service/:id/view',
        component: WitsServiceDetailComponent,
        resolve: {
            witsService: WitsServiceResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'WitsServices'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'wits-service/new',
        component: WitsServiceUpdateComponent,
        resolve: {
            witsService: WitsServiceResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'WitsServices'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'wits-service/:id/edit',
        component: WitsServiceUpdateComponent,
        resolve: {
            witsService: WitsServiceResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'WitsServices'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const witsServicePopupRoute: Routes = [
    {
        path: 'wits-service/:id/delete',
        component: WitsServiceDeletePopupComponent,
        resolve: {
            witsService: WitsServiceResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'WitsServices'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
