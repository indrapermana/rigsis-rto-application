/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { RtoAppTestModule } from '../../../test.module';
import { WellboreDeleteDialogComponent } from 'app/entities/wellbore/wellbore-delete-dialog.component';
import { WellboreService } from 'app/entities/wellbore/wellbore.service';

describe('Component Tests', () => {
    describe('Wellbore Management Delete Component', () => {
        let comp: WellboreDeleteDialogComponent;
        let fixture: ComponentFixture<WellboreDeleteDialogComponent>;
        let service: WellboreService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [RtoAppTestModule],
                declarations: [WellboreDeleteDialogComponent]
            })
                .overrideTemplate(WellboreDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(WellboreDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(WellboreService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('confirmDelete', () => {
            it(
                'Should call delete service on confirmDelete',
                inject(
                    [],
                    fakeAsync(() => {
                        // GIVEN
                        spyOn(service, 'delete').and.returnValue(of({}));

                        // WHEN
                        comp.confirmDelete(123);
                        tick();

                        // THEN
                        expect(service.delete).toHaveBeenCalledWith(123);
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
                    })
                )
            );
        });
    });
});
