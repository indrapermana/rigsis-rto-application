import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IUnitType } from 'app/shared/model/unit-type.model';
import { Principal } from 'app/core';
import { UnitTypeService } from './unit-type.service';

@Component({
    selector: 'jhi-unit-type',
    templateUrl: './unit-type.component.html'
})
export class UnitTypeComponent implements OnInit, OnDestroy {
    unitTypes: IUnitType[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private unitTypeService: UnitTypeService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {}

    loadAll() {
        this.unitTypeService.query().subscribe(
            (res: HttpResponse<IUnitType[]>) => {
                this.unitTypes = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    ngOnInit() {
        this.loadAll();
        this.principal.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInUnitTypes();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: IUnitType) {
        return item.id;
    }

    registerChangeInUnitTypes() {
        this.eventSubscriber = this.eventManager.subscribe('unitTypeListModification', response => this.loadAll());
    }

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
