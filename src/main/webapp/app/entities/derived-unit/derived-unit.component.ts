import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IDerivedUnit } from 'app/shared/model/derived-unit.model';
import { Principal } from 'app/core';
import { DerivedUnitService } from './derived-unit.service';

@Component({
    selector: 'jhi-derived-unit',
    templateUrl: './derived-unit.component.html'
})
export class DerivedUnitComponent implements OnInit, OnDestroy {
    derivedUnits: IDerivedUnit[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private derivedUnitService: DerivedUnitService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {}

    loadAll() {
        this.derivedUnitService.query().subscribe(
            (res: HttpResponse<IDerivedUnit[]>) => {
                this.derivedUnits = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    ngOnInit() {
        this.loadAll();
        this.principal.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInDerivedUnits();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: IDerivedUnit) {
        return item.id;
    }

    registerChangeInDerivedUnits() {
        this.eventSubscriber = this.eventManager.subscribe('derivedUnitListModification', response => this.loadAll());
    }

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
